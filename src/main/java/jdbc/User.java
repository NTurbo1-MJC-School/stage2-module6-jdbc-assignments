package jdbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    public static void main(String[] args) {
        try {
            Connection conn = CustomDataSource.getInstance().getConnection();

            if (conn == null) {
                System.out.println("FAIL");
            } else {
                System.out.println("SUCCESS");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
