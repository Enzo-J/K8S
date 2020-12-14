package com.wenge.tbase.harbor.service;

import com.wenge.tbase.harbor.bean.Artifacts;
import com.wenge.tbase.harbor.bean.Projects;
import com.wenge.tbase.harbor.bean.Repositories;
import com.wenge.tbase.harbor.result.RestResult;

import java.util.HashMap;

public interface HarborServiceService {


	RestResult<?> getProjectSummaryByIdService(Integer project_id);

	RestResult<?> getProjectStorageService();

	RestResult<?> getProjectStatisticsService();

	RestResult<?> getArtifactsListService(Artifacts artifacts);

	RestResult<?> getRepositoriesListService(Repositories repositories);

	RestResult<?> getProjectsListService(Projects projects);

	RestResult<?> addProjectsService(Projects projects);

	RestResult<?> updateProjectsService(Projects projects);

}
