package http;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import CustomTasks.SendHttpRequestTask;

/**
 * Created by Alpha on 11/30/2015.
 */
public class DbHandler {
    public static String getEventsUser(int user) throws ExecutionException, InterruptedException {
        ArrayList<MethodParameters> tmp = new ArrayList<MethodParameters>();

        MethodParameters head = new MethodParameters();
        head.name = "http://mbs.home.pl/kreator/Android/v1/events";
        head.value = "POST";

        MethodParameters payload = new MethodParameters();
        payload.name = "users";
        payload.value = Integer.toString(user);

        tmp.add(head);
        tmp.add(payload);
        String json = new SendHttpRequestTask().execute(tmp).get();
        return json;
    }

    public static String getEventsOwner(int owner) throws ExecutionException, InterruptedException {
        ArrayList<MethodParameters> tmp = new ArrayList<MethodParameters>();

        MethodParameters head = new MethodParameters();
        head.name = "http://mbs.home.pl/kreator/Android/v1/events/owner";
        head.value = "POST";

        MethodParameters payload = new MethodParameters();
        payload.name = "users";
        payload.value = Integer.toString(owner);

        tmp.add(head);
        tmp.add(payload);
        String json = new SendHttpRequestTask().execute(tmp).get();
        return json;
    }

    public static String getUserData(int user) throws ExecutionException, InterruptedException {
        ArrayList<MethodParameters> tmp = new ArrayList<MethodParameters>();

        MethodParameters head = new MethodParameters();
        head.name = "http://mbs.home.pl/kreator/Android/v1/users";
        head.value = "POST";

        MethodParameters payload = new MethodParameters();
        payload.name = "users";
        payload.value = Integer.toString(user);

        tmp.add(head);
        tmp.add(payload);
        String json = new SendHttpRequestTask().execute(tmp).get();
        return json;
    }

    public static String getEventMessages(int conversation) throws ExecutionException, InterruptedException {

        ArrayList<MethodParameters> tmp = new ArrayList<MethodParameters>();

        MethodParameters head = new MethodParameters();
        head.name = "http://mbs.home.pl/kreator/Android/v1/messages";
        head.value = "POST";

        MethodParameters payload = new MethodParameters();
        payload.name = "conversation";
        payload.value = Integer.toString(conversation);
        tmp.add(head);
        tmp.add(payload);
        String json = new SendHttpRequestTask().execute(tmp).get();
        return json;
    }
}
