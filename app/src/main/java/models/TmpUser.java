package models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by adam on 19.01.16.
 */
@ParseClassName("User")
public class TmpUser extends ParseObject {
        public TmpUser() {super();}

        //GETTERS
        public String getUsername(){
            return getString("username");
        }
        public String getEmail()
        {
            return getString("email");
        }

        public void setUsername(String username)
        {
            put("username", username);
        }
}
