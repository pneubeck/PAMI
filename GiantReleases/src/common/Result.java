
package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

	@SerializedName("deck")
	@Expose
	private String deck;
	@SerializedName("expected_release_day")
	@Expose
	private Object expectedReleaseDay;
	@SerializedName("expected_release_month")
	@Expose
	private Object expectedReleaseMonth;
	@SerializedName("expected_release_year")
	@Expose
	private Object expectedReleaseYear;
	@SerializedName("image")
	@Expose
	private Image image;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("original_release_date")
	@Expose
	private String originalReleaseDate;
	@SerializedName("platforms")
	@Expose
	private List<Platform> platforms = null;

	public String getDeck() {
		return deck;
	}

	public void setDeck(String deck) {
		this.deck = deck;
	}

	public Object getExpectedReleaseDay() {
		return expectedReleaseDay;
	}

	public void setExpectedReleaseDay(Object expectedReleaseDay) {
		this.expectedReleaseDay = expectedReleaseDay;
	}

	public Object getExpectedReleaseMonth() {
		return expectedReleaseMonth;
	}

	public Date getExpectedReleaseDate() {
		Date date;
		if (expectedReleaseDay == null && expectedReleaseMonth != null && expectedReleaseYear != null) {
			try {
				date = new SimpleDateFormat("yyyyMM")
						.parse(expectedReleaseYear.toString() + expectedReleaseMonth.toString());
				return date;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (expectedReleaseDay != null && expectedReleaseMonth != null && expectedReleaseYear != null) {
			try {
				date = new SimpleDateFormat("yyyyMMdd").parse(expectedReleaseYear.toString()
						+ expectedReleaseMonth.toString() + expectedReleaseDay.toString());
				return date;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
		}else if(expectedReleaseDay == null && expectedReleaseMonth == null && expectedReleaseYear != null){
			try {
				date = new SimpleDateFormat("yyyy").parse(expectedReleaseYear.toString());
				return date;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
		}
		return null;
	}

	public void setExpectedReleaseMonth(Object expectedReleaseMonth) {
		this.expectedReleaseMonth = expectedReleaseMonth;
	}

	public Object getExpectedReleaseYear() {
		return expectedReleaseYear;
	}

	public void setExpectedReleaseYear(Object expectedReleaseYear) {
		this.expectedReleaseYear = expectedReleaseYear;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalReleaseDate() {
		return originalReleaseDate;
	}

	public void setOriginalReleaseDate(String originalReleaseDate) {
		this.originalReleaseDate = originalReleaseDate;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}

	@Override
	public String toString() {
		return "Result [deck=" + deck + ", expectedReleaseDay=" + expectedReleaseDay + ", expectedReleaseMonth="
				+ expectedReleaseMonth + ", expectedReleaseYear=" + expectedReleaseYear + ", image=" + image + ", name="
				+ name + ", originalReleaseDate=" + originalReleaseDate + ", platforms=" + platforms + "]";
	}

}
