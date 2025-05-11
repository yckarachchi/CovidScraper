package lk.ac.pdn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CovidScraper {

	public static void main(String[] args) throws IOException {
		
		final String url ="https://www.worldometers.info/coronavirus/";
		
		CovidData globalData = scrapeGlobalData(url);
		System.out.println("Global Covid-19 Data :");
		System.out.println(globalData.toString());
		
		List<CountryData> countryData = scrapeCountryData(url);
		System.out.println("Countries by Cases :");
		countryData.stream().limit(10).forEach(System.out::println);
		
	}
	private static CovidData scrapeGlobalData(String url) throws IOException  {
		org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
		
		CovidData data = new CovidData();
		data.setTotalCases(doc.select("div.maincounter-number > span").get(0).text());
		data.setTotalDeaths(doc.select("div.maincounter-number > span").get(1).text());
		data.setTotalRecovers(doc.select("div.maincounter-number > span").get(2).text());
		data.setActiveCases(doc.select("div.number-table-main").get(0).text());
		data.setClosedCases(doc.select("div.number-table-main").get(1).text());
		
		return data;
	}
	
	private static List<CountryData> scrapeCountryData(String url) throws IOException {
		
		Document doc = Jsoup.connect(url).get();
		
		List <CountryData> countries = new ArrayList<>();
		Elements rows = doc.select("table#main_table_countries_today tbody tr ");
		
		for(org.jsoup.nodes.Element row: rows) {
			Elements cols = row.select("td");
			if(cols.size() >1) {
				CountryData country = new CountryData();
				country.setName(cols.get(1).text());
				country.setTotalCases(cols.get(2).text());
				country.setNewCases(cols.get(3).text());
				country.setTotalDeaths(cols.get(4).text());
				country.setNewDeaths(cols.get(5).text());
				country.setTotalRecoverd(cols.get(6).text());
				
				countries.add(country);
			}
		}
		return countries;
		
	}

	//structured our downloaded data
	static class CovidData{
		private String totalCases;
		private String totalDeaths;
		private String totalRecovers;
		private String activeCases;
		private String closedCases;
		
		
		//getters and setters
		public String getTotalCases() {
			return totalCases;
		}
		public void setTotalCases(String totalCases) {
			this.totalCases = totalCases;
		}
		public String getTotalDeaths() {
			return totalDeaths;
		}
		public void setTotalDeaths(String totalDeaths) {
			this.totalDeaths = totalDeaths;
		}
		public String getTotalRecovers() {
			return totalRecovers;
		}
		public void setTotalRecovers(String totalRecovers) {
			this.totalRecovers = totalRecovers;
		}
		public String getActiveCases() {
			return activeCases;
		}
		public void setActiveCases(String activeCases) {
			this.activeCases = activeCases;
		}
		public String getClosedCases() {
			return closedCases;
		}
		public void setClosedCases(String closedCases) {
			this.closedCases = closedCases;
		}
		
		
		public String toString() {
			return String.format(
					"Total Cases: %s\nTotal Deaths: %s\nTotal Recovered: %s\n"
					+ "Active Cases : %s\nClosed Cases : %s",totalCases, totalDeaths, totalRecovers, activeCases, closedCases );
		}
		
	}
	static class CountryData{
		private String name;
		private String totalCases;
		private String newCases;
		private String totalDeaths;
		private String newDeaths;
		private String totalRecoverd;
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTotalCases() {
			return totalCases;
		}
		public void setTotalCases(String totalCases) {
			this.totalCases = totalCases;
		}
		public String getNewCases() {
			return newCases;
		}
		public void setNewCases(String newCases) {
			this.newCases = newCases;
		}
		public String getTotalDeaths() {
			return totalDeaths;
		}
		public void setTotalDeaths(String totalDeaths) {
			this.totalDeaths = totalDeaths;
		}
		public String getNewDeaths() {
			return newDeaths;
		}
		public void setNewDeaths(String newDeaths) {
			this.newDeaths = newDeaths;
		}
		
		
		
		public String toString() {
			return String.format(
					"Total Cases: %-20s | Cases: %10s | New: %8s | Recovered: %-10s",
					name, totalCases, newCases, totalDeaths, totalRecoverd);
		}
	}

}
