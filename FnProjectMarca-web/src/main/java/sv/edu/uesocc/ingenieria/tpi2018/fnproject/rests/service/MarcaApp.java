/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.tpi2018.fnproject.rests.service;

import com.jrestless.fnproject.FnFeature;
import com.jrestless.fnproject.FnRequestHandler;
import org.glassfish.jersey.server.ResourceConfig;

public class MarcaApp extends FnRequestHandler {

    public MarcaApp() {

        ResourceConfig config = new ResourceConfig();
        config.packages(getClass().getPackage().getName());  // scan all classes in this package
        config.register(FnFeature.class);

        init(config);

        start();
    }
    
}
