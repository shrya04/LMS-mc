import com.test.lms.controller.LMSController;
import com.test.lms.models.User;
import com.test.lms.service.UserService;

import java.util.Scanner;

public class LMS {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

//        System.out.println("test");
        UserService.adduser(new User("u1", "ram"));
        UserService.adduser(new User("u2", "shyam"));
        UserService.adduser(new User("u3", "sita"));
        UserService.adduser(new User("u4", "gita"));

        while (true) {

            try {
                String cmds = sc.nextLine();
                String[] input = cmds.split(" ");
                switch (input[0]) {
                    case "add_book": //bookname authorf authorl
                        LMSController.addBook(input[1], input[2] + " " + input[3]);
                        break;
                    case "borrow_book": //userid bookId borrowTime
                        LMSController.reserveBook(input[1], input[2], input[3]);
                        break;
                    case "return_book": //userId bookId returnTime
                        LMSController.returnBook(input[1], input[2], input[3]);
                        break;
                    case "remove_user"://userId
                        UserService.removeUser(input[1]);
                        break;
                    case "calculate_fine"://userId
                        LMSController.calculateFine(input[1]);
                        break;
                    case "borrowed_books_by_user"://userId
                        LMSController.showBooks(input[1]);
                        break;
                    default:
                        throw new Exception("Unsupported action");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
