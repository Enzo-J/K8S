package com.wenge.tbase.harbor.service;

import com.wenge.tbase.harbor.bean.Artifacts;
import com.wenge.tbase.harbor.bean.Projects;
import com.wenge.tbase.harbor.bean.Repositories;
import com.wenge.tbase.harbor.result.RestResult;

import java.util.HashMap;

public interface HarborServiceService {

	RestResult<?> getArtifactsListService(Artifacts artifacts);

	RestResult<?> getRepositoriesListService(Repositories repositories);

	RestResult<?> getProjectsListService(Projects projects);

	RestResult<?> addProjectsListService(Projects projects);

	RestResult<?> updateProjectsListService(Projects projects);



	RestResult<?> getImageByNamespaceAppId(HashMap<String, Object> serviceMap);

	RestResult<?> getImageByNamespaceAppName(HashMap<String, Object> serviceMap);
}
