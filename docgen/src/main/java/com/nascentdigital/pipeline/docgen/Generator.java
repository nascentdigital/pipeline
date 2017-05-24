package com.nascentdigital.pipeline.docgen;

import com.github.javaparser.JavaParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.body.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nascentdigital.pipeline.Grouping;
import com.nascentdigital.pipeline.Pipeline;
import com.nascentdigital.pipeline.annotations.Group;
import com.nascentdigital.pipeline.annotations.GroupType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Generator {

    public static String readSample(String filePath) throws IOException{
        String line;
        String str="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("docgen/samples/" + filePath));

            line = reader.readLine();
            line = reader.readLine();
            while (true) {
                str += line;
                line = reader.readLine();
                if(line == null) break;
                str +="\n";
            }
        }catch(Throwable ex){
            System.err.println("File with file path: " + filePath + " is missing.");
        }
        return str;
    }

    public static void main(String[] args) throws IOException {

        // fail if file doesn't exist
        File pipelineFile = args.length == 0
            ? null
            : new File(args[0]);
        if (pipelineFile == null
                || !pipelineFile.exists()) {
            System.err.println("Invalid file specified: " + pipelineFile);
            System.exit(1);
            return;
        }

        // parse file
        CompilationUnit compilationUnit = JavaParser.parse(pipelineFile);

        // fail if pipeline isn't loaded
        Optional<ClassOrInterfaceDeclaration> pipelineClassRef =
                compilationUnit.getClassByName("Pipeline");
        if (!pipelineClassRef.isPresent()) {
            System.err.println("Unable to load pipeline from file: " + pipelineFile);
            System.exit(1);
            return;
        }

        // fail if pipeline can't be resolved
        ClassOrInterfaceDeclaration pipelineClass = pipelineClassRef.get();
        if (pipelineClass == null) {
            System.err.println("Unable to resolve Pipeline class from file: " + pipelineFile);
            System.exit(1);
            return;
        }

        // create mapping of method to
        Map<String, MethodDeclaration> methodMetadataMap = Pipeline.from(pipelineClass.getMethods())
                .toMap(md -> {
                    String signature = Pipeline.from(md.getParameters())
                            .map(p -> p.getType().toString())  //using just the parameter types
                            .join(",");
                    return md.getName().toString() + ":" + signature;
                });

        // define document

        // process all pipeline groups
        Pipeline<Grouping<Group, Method>> methodGroups = Pipeline.from(Pipeline.class.getDeclaredMethods())
                .where(m -> Modifier.isPublic(m.getModifiers()))
                .groupBy(m -> m.getAnnotation(Group.class));

        JSONObject obj = new JSONObject();

        for (Grouping<Group, Method> methodGroup : methodGroups) {

            JSONArray methodArray = new JSONArray();
            GroupType annoType = methodGroup.iterator().next().getAnnotation(Group.class).type();

            // add all methods to the group
            JSONObject methodObj;
            for (Method method : methodGroup) {
               methodObj = new JSONObject();

                // get associated metadata for method
                String methodKey = method.getName() + ":" + Pipeline.from(method.getParameters())
                        .map(p -> {
                            String st = p.getParameterizedType().getTypeName().replace("java.lang.","");
                            st = st.replace("com.nascentdigital.pipeline.","");
                            return st;
                        })
                        .join(",");
                try {
                    // get the actual method declaration related to the method
                    MethodDeclaration methodMeta = methodMetadataMap.get(methodKey);

                    String key= Pipeline.from(methodKey.split(",| |<|>|\\[\\]|:")).join("");
                   // methodObj.put("Key",methodKey);
                    methodObj.put("MethodKey", key);
                    methodObj.put("MethodName", methodMeta.getName());

                    methodObj.put("ReturnType", methodMeta.getType());

                    String name = method.getName() + "(" + Pipeline.from(method.getParameterTypes())
                            .map(p->{
                                String str = p.getTypeName().replace("java.lang.","")
                                        .replace("com.nascentdigital.pipeline.","");
                                return str;
                            }).join(",") +")";
                    methodObj.put("Key",name);

                    String comment = "";

                    try{
                        comment = methodMeta.getComment().toString();
                    }
                    catch (Throwable ex){}
                    try {
                        comment = methodMeta.getJavadoc().getDescription().toText();
                    }catch (Throwable ex){
                    }
                    finally {
                        methodObj.put("Comment", comment);
                    }

                    JSONArray paramArray = new JSONArray();

                    for (Parameter param : methodMeta.getParameters()) {
                        JSONObject paramObj = new JSONObject();
                        paramObj.put("Type", param.getType());
                        paramObj.put("Name", param.getName());
                        paramArray.put(paramObj);
                    }

                    String sample = readSample(annoType.toString()+"/"+methodKey+".md");
                    methodObj.put("Example",sample);
                    methodObj.put("Parameters",(Object)paramArray);
                    methodArray.put(methodObj);
                    obj.put(annoType.name, methodArray);
                }
                catch (Throwable ex){}
            }
        }

     System.out.println(obj.toString(2));

    }
}
