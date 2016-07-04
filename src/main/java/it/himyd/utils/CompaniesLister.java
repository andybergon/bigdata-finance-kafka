package it.himyd.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CompaniesLister {
	private static final String EU_COMPANY_LIST = "/company-lists/stocks-eu.csv";
	private static final String EU_FTSE100_COMPANY_LIST = "/company-lists/stocks-eu-ftse100.csv";
	private static final String US_COMPANY_LIST = "/company-lists/stocks-us.csv";
	private static final String NASDAQ_COMPANY_LIST = "/company-lists/nasdaq-company-list.csv";

	public List<String> listCompanies() {
		List<String> companies = new ArrayList<String>();

		String csvFile = EU_COMPANY_LIST;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			InputStream is = getClass().getResourceAsStream(csvFile);
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(new BufferedReader(isr));
			// br.readLine(); // skip description

			while ((line = br.readLine()) != null) {
				String[] company = line.split(cvsSplitBy);
				String companyName = company[0];
				companyName = companyName.trim();

				companies.add(companyName);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return companies;

	}

	public String[] listCompaniesArray() {
		List<String> companiesList = listCompanies();
		String[] companies = companiesList.toArray(new String[companiesList.size()]);
		return companies;
	}

	public List<String> listNASDAQcompanies() {
		List<String> companies = new ArrayList<String>();

		String csvFile = NASDAQ_COMPANY_LIST;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine(); // skip description

			while ((line = br.readLine()) != null) {
				String[] company = line.split(cvsSplitBy);
				String companyName = company[0];
				companyName = companyName.substring(1, companyName.length() - 1);
				companyName = companyName.trim();

				companies.add(companyName);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return companies;

	}

	public String[] listNASDAQcompaniesArray() {
		List<String> companiesList = listNASDAQcompanies();
		String[] companies = companiesList.toArray(new String[companiesList.size()]);
		return companies;
	}

	public static void main(String[] args) {
		CompaniesLister cl = new CompaniesLister();
		List<String> companies = cl.listCompanies();
		System.out.println("companies # " + companies.size());
		System.out.println(companies.toString());

		// for (String company : companies) {
		// System.out.println(company);
		// }
	}
}
