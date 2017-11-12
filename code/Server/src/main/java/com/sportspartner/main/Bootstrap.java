
package com.sportspartner.main;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.sportspartner.controllers.*;
import com.sportspartner.dao.impl.DeviceRegistrationDaoImpl;
import com.sportspartner.dao.impl.NotificationDaoImpl;
import com.sportspartner.model.DeviceRegistration;
import com.sportspartner.model.Notification;
import com.sportspartner.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;
import com.sportspartner.util.*;
import org.sql2o.tools.IOUtils;



public class Bootstrap {
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 8080;

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * Set the ip address and port , start different services
     * @param args arguments of main function
     * @throws Exception throw exception of Service
     */

    public static void main(String[] args) throws Exception {

        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        try {
            UserService userModel = new UserService();
            new LoginController(userModel);
            new SignUpController(userModel);
            new LogoutController(userModel);

            ProfileService profileModel = new ProfileService();
            new ProfileController(profileModel);

            ActivityService activityModel = new ActivityService();
            new ActivityController(activityModel);

            FriendService friendService = new FriendService();
            new FriendController(friendService);

            ImageService imageService = new ImageService();
            new ImageController(imageService);

            FacilityService facilityService = new FacilityService();
            new FacilityController(facilityService);


        } catch (Exception ex) {
            logger.error("Failed to create a SportsPartnerService instance. Aborting");
        }
        

    /*
        Sender sender = new Sender("AIzaSyD6mj4I5YTNU-copAr7HY_LZ7Rwz_jcK4U");

        Message.Builder builder= new Message.Builder();
        builder.addData("text","hello!");
        Message message = builder.build();
        String devices = "";
        Result result = sender.send(message, devices, 5);
        System.out.println(result.getErrorCodeName());

        if (result.getMessageId() != null) {
            String canonicalRegId = result.getCanonicalRegistrationId();
            System.out.println(canonicalRegId);
            if (canonicalRegId != null) {
                // same device has more than on registration ID: update database
                System.out.println("same device has more than on registration ID: update database!");
            }
        } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                // application has been removed from device - unregister database
                System.out.println("application has been removed from device - unregister database!");
            }
        }
        */


//        ImageUtil imageUtil = new ImageUtil();
//        BufferedImage image = imageUtil.getImage("./res/sporticon/006_origin.png");
//        BufferedImage image1 = imageUtil.resizeImage(image);
//        imageUtil.saveImage(image1, "./res/sporticon/006_small.png");

//        ImageUtil imageUtil = new ImageUtil();
//        BufferedImage image = imageUtil.getImage("./res/usericon/shirish@gmail.com_origin.png");
//        BufferedImage resizedimage = imageUtil.resizeImage(image);
//        String base64 = imageUtil.imageToBase64(resizedimage);
//        imageUtil.saveImage(imageUtil.base64ToImage(base64),"./res/usericon/shirish@gmail.com_small.png") ;
//        imageUtil.saveImage(resizedimage,"./res/usericon/shirish1@gmail.com_small.png");




        /*
        String uuid = UUID.randomUUID().toString();
        Date time = new Date(System.currentTimeMillis());
        Notification notification = new Notification("u1",uuid,"title","detail","NORESPONSE","u24",time,1,0);
        NotificationDaoImpl f1 = new NotificationDaoImpl();
        boolean test = false;
        test = f1.newNotification(notification);
        System.out.println(test);
        List<Notification> notifications = f1.getUnsentNotification("u1");
        System.out.println(notifications.get(0).getNotificationTitle());
        boolean test1 = f1.setNotificationSent("u1",uuid);
        System.out.println(test1);
        boolean test2 = f1.deleteNotification(notification);
        System.out.println(test2);
                /*
        AuthorizationDaoImpl f1 = new AuthorizationDaoImpl();
        Authorization newAuthorization = new Authorization("zihao@jhu.edu","666");
        f1.newAuthorization(newAuthorization);

        boolean test = f1.newAuthorization(newAuthorization);
        System.out.println(test);
        List<Authorization> list = f1.getAllAuthorizations();
        System.out.println(list.size());
        f1.deleteAuthorization(newAuthorization);
        list = f1.getAllAuthorizations();
        System.out.println(list.size());

        ActivityDaoImpl f1 = new ActivityDaoImpl();
        Date startTime = new Date(System.currentTimeMillis());
        Date endTime = new Date(System.currentTimeMillis()+10);
        Activity newActivity = new Activity("a002", "u1", "001", "OPEN", "003", startTime, endTime, 4, 3 , "Do nothing!");
        f1.newActivity(newActivity);
        boolean test = f1.deleteActivity(newActivity);
        System.out.println(test);
        */
        /*
        ActivityMemberDaoImpl f1 = new ActivityMemberDaoImpl();
        ActivityCommentDaoImpl f2 = new ActivityCommentDaoImpl();
        ActivityMember newMember = new ActivityMember("a001","u24");
        ActivityComment newComment = new ActivityComment("a001","002","u1",new Date(System.currentTimeMillis()),"Good activity!");
        boolean test = false;
        //List <ActivityMember> members = f1.getAllActivitymembers("a001");
        test = f2.newActivityComment(newComment);
        System.out.println(test);
        test = f2.hasActivityComment(newComment);
        System.out.println(test);

        test = f2.deleteActivityComment(newComment);
        System.out.println(test);
        List <ActivityComment> comments = f2.getAllActivityComments("a001");
        System.out.println(comments.get(0).getContent());
        */
        



        /*test for interest*/
//        InterestDaoImpl in = new InterestDaoImpl();
//        Interest interest = new Interest("u1","001");
//        in.newInterest(interest);
//        Interest interest1 = new Interest("u1","002");
//        in.newInterest(interest1);
        /*test for FriendDaoImpl:*/
//        UserDaoImpl u = new UserDaoImpl();
//        User user = new User("u1", "p1", "PERSON");
//        u.newUser(user);
//        UserDaoImpl u2 = new UserDaoImpl();
//        User user2 = new User("u2", "p2", "PERSON");
//        u2.newUser(user2);
//
//        FriendDaoImpl f1 = new FriendDaoImpl();
//        f1.newFriend("u1", "u2");
//        FriendDaoImpl f2 = new FriendDaoImpl();
//        f2.newFriend("u2", "u1");
//        FriendDaoImpl f3 = new FriendDaoImpl();
//        List<User> users = f3.getAllFriends("u1");
//        System.out.println(users.get(0).getUserId());

        /*test for PersonDaoImpl:*/
        /*
        PersonDaoImpl piml = new PersonDaoImpl();
        UserDaoImpl uimp = new UserDaoImpl();
        piml.deletePerson("zihao@jhu.edu");
        uimp.deleteUser("xuan@jhu.edu");
        uimp.deleteUser("zihao@jhu.edu");
        User user = new User("xuan@jhu.edu", "lovejhu", "PERSON");
        User user1 = new User("zihao@jhu.edu", "123456", "PERSON");
        uimp.newUser(user);
        uimp.newUser(user1);
        Person person1 = new Person("xuan@jhu.edu", "lovejhu", "PERSON", "xuan zhang", "Baltimore", "female", 23, 4.5, 4, 5, 4, "server/res/icon/xuan@jhu.edu");
        Person person2 = new Person("zihao@jhu.edu", "123456", "PERSON", "zihao xiao", "Baltimore", "male", 23, 4.5, 4, 5, 4, "server/res/icon/zihao@jhu.edu");
        Person person3 = new Person("zihao@jhu.edu", "123456", "PERSON", "xiaochen li", "Beijing", "male", 23, 4.5, 4, 5, 4, "server/res/icon/zihao@jhu.edu");
        System.out.println(person1.getUserId());
        piml.newPerson(person1);
        piml.newPerson(person2);
        Person p2 = piml.getPerson("xuan@jhu.edu");
        System.out.println(p2.getAddress());
        piml.deletePerson("xuan@jhu.edu");
        piml.updatePerson(person3);
        List<Person> persons = piml.getAllPersons();
        System.out.println(persons.get(0).getAddress());
        */
        //FriendDaoImpl f3 = new FriendDaoImpl();
        //List<User> users = f3.getAllFriends("u1");
        //System.out.println(users.get(0).getUserId());

/*
        // Test for Interest

        // New
        InterestDaoImpl f3 = new InterestDaoImpl();
        // Test: Get all

        List<Interest> interests = f3.getAllInterests();
        System.out.println(interests.get(0).getSportId());
        // Test: Get single
        Interest single_interest = f3.getInterest("123@jhu.edy");
        System.out.println(single_interest.getSportId());
        // Test: Insert
        Interest newInterest = new Interest("666@gmail.com","002");
        boolean inserttest  = f3.newInterest(newInterest);
        System.out.println(inserttest);

        //Test: Update
        boolean updatetest = f3.updateInterest(newInterest,"003");
        System.out.println(updatetest);

        //Test: Delete
        Interest newInterest_update = new Interest("666@gmail.com","003");
        boolean deletetest = f3.deleteInterest(newInterest_update);
        System.out.println(updatetest);
*/

/*
        //Test for Sport
        //New
        SportDaoImpl f4 = new SportDaoImpl();
        //Get all test
        List<Sport> sports = f4.getAllSports();
        System.out.println(sports.get(0).getSportId());

        // Test: Get single
        Sport single_sport = f4.getSport("001");
        System.out.println(single_sport.getSportName());

        // Test: Insert
        Sport newSport = new Sport("100","Basketball","/image/sporticon/002.png");
        boolean isinsert  = f4.newSport(newSport);
        System.out.println(isinsert);

        //Test: Update
        boolean isupdate = f4.updateSport(new Sport("100","Basketball","/image/sporticon/666.png"));
        System.out.println(isupdate);

        //Test: Delete
        boolean isdelete = f4.deleteSport("100");
        System.out.println(isdelete);
        */


        //Specify the IP address and Port at which the server should be run
        /*
        ipAddress(IP_ADDRESS);
        port(PORT);
        //Specify the sub-directory from which to serve static resources (like html and css)
        staticFileLocation("/public");

        //Create the model instance and then configure and start the web service
        try {
            UserService model = new UserService();
            new LoginController(model);
            new SignUpController(model);
        } catch (Exception ex) {
            logger.error("Failed to create a GameService instance. Aborting");
        }
    }
*/
        //SportDaoImpl f4 = new SportDaoImpl();
        //Sport newSport = new Sport("100","Basketball","/image/sporticon/002.png");
        //boolean isinsert  = f4.newSport(newSport);
        //System.out.println(isinsert);
        //newSport = new Sport("100","Basketball","/image/sporticon/002.png");
        //isinsert  = f4.newSport(newSport);
        //System.out.println(isinsert);
    }
}
