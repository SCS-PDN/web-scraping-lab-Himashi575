import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static void main(String[] args) {
        final String url = "https://bbc.com";

        try {
            BBCScraping scraper = new BBCScraping();
            scraper.scrape(url);
            System.out.println(scraper);
        } catch (IOException e) {
            System.out.println("Error fetching the webpage: " + e.getMessage());
        }
    }

    static class BBCScraping {
        private String title;
        private String headings;
        private String links;

        public void scrape(String url) throws IOException {
            Document doc = Jsoup.connect(url).get();

            // Title
            this.title = doc.title();

            // Headings
            StringBuilder headingBuilder = new StringBuilder();
            for (int i = 1; i <= 6; i++) {
                Elements headingElements = doc.select("h" + i);
                for (Element heading : headingElements) {
                    headingBuilder.append("H").append(i).append(": ").append(heading.text()).append("\n");
                }
            }
            this.headings = headingBuilder.toString();

            // Links
            StringBuilder linkBuilder = new StringBuilder();
            Elements linkElements = doc.select("a[href]");
            for (Element link : linkElements) {
                linkBuilder.append(link.text()).append(" -> ").append(link.absUrl("href")).append("\n");
            }
            this.links = linkBuilder.toString();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHeadings() {
            return headings;
        }

        public void setHeadings(String headings) {
            this.headings = headings;
        }

        public String getLinks() {
            return links;
        }

        public void setLinks(String links) {
            this.links = links;
        }

        public String toString() {
            return String.format(
                    "Title:\n%s\n\nHeadings:\n%s\nLinks:\n%s",
                    title, headings, links
            );
        }
    }
}