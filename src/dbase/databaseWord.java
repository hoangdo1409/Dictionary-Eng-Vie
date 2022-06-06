package dbase;

import java.sql.*;

public class databaseWord {
    private static final String url = "jdbc:mysql://localhost:3306/dictionarynew";
    private static final String user = "root";
    private static final String password = "D280601a";

    public static Connection conn = connect();

    /**
     * kết nối tới database.
     * @return biến conn
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Tìm từ.
     * @param wordNeedFind từ cần tìm
     * @return nội dung của từ
     */
    public static String showWordNeedFind(String wordNeedFind){
        try {
            Statement statement = conn.createStatement();
            String sql2 = "SELECT * FROM word w WHERE w.vocabulary = " + "\"" + wordNeedFind + "\";";
            ResultSet rs = statement.executeQuery(sql2);
            while (rs.next()) {
                String mean = rs.getString(3).replace("+", ":")
                        .replace("=", "\n\t= ");
                mean = mean.replace("*", "\n\n*");
                mean = mean.replace("-", "\n\t- ");
                return (rs.getString(1) + " - " + rs.getString(2) + mean);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    /**
     * Sửa từ.
     * @param wordNeedEdit từ có nội dung cần sửa
     * @param stringAfterEdit chuỗi thay thế
     * @param bool: true - sửa nghĩa , false - sửa phiên âm
     * @return true if sửa thành công, false: thất bại
     */
    public static boolean editSpellAndMean(String wordNeedEdit, String stringAfterEdit, boolean bool) {
        try {
            if (bool) {
                String sql_update_read = "UPDATE word SET mean_information = \"" + stringAfterEdit
                        + "\" WHERE word.vocabulary = \"" + wordNeedEdit + "\";";
                PreparedStatement pstmt = conn.prepareStatement(sql_update_read);
                pstmt.executeUpdate();
                return true;
            } else {
                String sql_update_read = "UPDATE word SET spelling = \"" + stringAfterEdit
                        + "\" WHERE word.vocabulary = \"" + wordNeedEdit + "\";";
                PreparedStatement pstmt = conn.prepareStatement(sql_update_read);
                pstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Xóa từ.
     * @param wordNeedDel từ cần xóa
     * @return true or false
     */
    public static boolean deleteWord(String wordNeedDel) {
        try {
            Statement statement = conn.createStatement();
            String sql_deleteWord = "DELETE FROM `word` WHERE `word`.`vocabulary` = \"" + wordNeedDel + "\";";
            statement.executeUpdate(sql_deleteWord);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Thêm từ
     * @param wordNeedAdd từ cần thêm
     * @param spellNeedAdd phiên âm cần thêm
     * @param meanNeedAdd nghĩa cần thêm
     * @return true or false
     */
    public static boolean addWord(String wordNeedAdd, String spellNeedAdd, String meanNeedAdd) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sqlAddWord = "INSERT INTO `word` (`vocabulary`, `spelling`, `mean_information`) VALUES ( \'" + wordNeedAdd
                    + "\', \'" + spellNeedAdd
                    + "\', \'" + meanNeedAdd + "\');";
            statement.executeUpdate(sqlAddWord);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Kiểm tra từ đã có chưa.
     * @param wordNeedCheck từ cần kiêm tra
     * @return 1 nếu đã có else chuyển sang addWord()
     */
    public static boolean checkWordExisted(String wordNeedCheck) {
        Connection conn = connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql2 = "SELECT w.vocabulary FROM word w WHERE w.vocabulary = " + "\"" + wordNeedCheck + "\";";
            ResultSet resultSet2 = statement.executeQuery(sql2);
            
            if (resultSet2.next()) {
                String test = resultSet2.getString("w.vocabulary");
                return true;
            } else {
                return false;
            }
            
       
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }    
    }
    

    public static String[] hintWord(String testWord) {
        try {
        	String[] result = new String[10];
            Statement statement = conn.createStatement();
            String sql1 = "SELECT w.vocabulary FROM word w WHERE w.vocabulary LIKE \"%" + testWord + "%\" LIMIT 10;";
            ResultSet rs = statement.executeQuery(sql1);
            int i = 0;
            while (rs.next()) {
                result[i] = rs.getString("w.vocabulary");
                i++;
                //System.out.println(rs.getString("w.vocabulary"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
