package net.easycloud.web.rest;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import org.jetbrains.annotations.NotNull;

import javax.print.attribute.standard.PresentationDirection;

public class TemplateUploadRest implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        for (UploadedFile uploadedFile : context.uploadedFiles()) {
            System.out.println("file uplaoded: "+uploadedFile.filename());
        }
        UploadedFile serverFile = context.uploadedFile("serverFile");
        FileUtil.streamToFile(serverFile.content(), "templates/server.jar");
        context.redirect("/");
    }
}
