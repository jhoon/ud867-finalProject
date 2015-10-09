/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package pe.jota.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import pe.jota.jokelib.JokeLibrary;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.jota.pe",
                ownerName = "backend.builditbigger.jota.pe",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public JokeBean sayHi(@Named("name") String name) {
        JokeBean response = new JokeBean();
        response.setData("Hi, " + name);

        return response;
    }

    /**
     * A simple joke endpoint
     */
    @ApiMethod(name = "tellJoke")
    public JokeBean getJoke() {
        JokeBean response = new JokeBean();
        response.setData(JokeLibrary.getJoke());

        return response;
    }
}
