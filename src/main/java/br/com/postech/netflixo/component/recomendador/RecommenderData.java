package br.com.postech.netflixo.component.recomendador;

import br.com.postech.netflixo.domain.entity.NotaVideo;
import br.com.postech.netflixo.domain.repository.NotaVideoRepository;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommenderData {


	private NotaVideoRepository notaVideoRepository;

	public RecommenderData(NotaVideoRepository notaVideoRepository) {
		this.notaVideoRepository = notaVideoRepository;
	}

	public DataModel getModeloDeFilmes() {
		List<NotaVideo> notaVideosList = notaVideoRepository.findAll();
		Map<Long, Map<Long, Float>> data = new HashMap<>();
		for (NotaVideo notaVideo : notaVideosList) {
			long userId = notaVideo.getId();
			long itemId = notaVideo.getDescription();
			float preference = notaVideo.getNote();
			if (!data.containsKey(userId)) {
				data.put(userId, new HashMap<>());
			}
			data.get(userId).put(itemId, preference);
		}

		FastByIDMap<PreferenceArray> fastMap = new FastByIDMap<>();
		for (Map.Entry<Long, Map<Long, Float>> entry : data.entrySet()) {
			List<Preference> prefs = new ArrayList<>();
			for (Map.Entry<Long, Float> innerEntry : entry.getValue().entrySet()) {
				prefs.add(new GenericPreference(entry.getKey(), innerEntry.getKey(), innerEntry.getValue()));
			}
			fastMap.put(entry.getKey(), new GenericUserPreferenceArray(prefs));
		}
		return new GenericDataModel(fastMap);
	}
}