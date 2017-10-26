package web.watson;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

public class WatsonConnector {

	private NaturalLanguageUnderstanding service;
	private String url;

	public WatsonConnector(String username, String password, String url) {
		this.service = new NaturalLanguageUnderstanding(
				  NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
				  username,
				  password
				);
		this.url = url;
	}

	public AnalysisResults recognizeNaturalLanguage() {
		EntitiesOptions entities = new EntitiesOptions.Builder().sentiment(true).emotion(true).limit(20).build();
		CategoriesOptions categories = new CategoriesOptions();
		Features features = new Features.Builder().entities(entities).categories(categories).build();
		AnalyzeOptions parameters = new AnalyzeOptions.Builder().url(url).features(features).build();
		AnalysisResults results = service.analyze(parameters).execute();
		return results;
	}
}
