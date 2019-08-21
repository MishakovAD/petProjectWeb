import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class TestClass {
    public static void main(String[] args) throws IOException {
        URL document = new URL("https://ru.stackoverflow.com/questions/1015512/%d0%9f%d1%80%d0%b8-%d0%b7%d0%b0%d0%b3%d1%80%d1%83%d0%b7%d0%ba%d0%b5-%d0%ba%d0%b0%d1%80%d1%82%d0%b8%d0%bd%d0%ba%d0%b8-%d1%87%d0%b5%d1%80%d0%b5%d0%b7-https-%d0%be%d1%82%d1%81%d1%83%d1%82%d1%81%d0%b2%d1%83%d0%b5%d1%82-header-java/1015620?noredirect=1#comment1725456_1015620");
        HttpURLConnection conn = (HttpURLConnection) document.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(1000);
        String mimeType = conn.getHeaderField("Content-Type");
        //MimeEntry mimeEntry = mimeTypes.find(mimeType.replaceAll(";.*", ""));
    }
}
