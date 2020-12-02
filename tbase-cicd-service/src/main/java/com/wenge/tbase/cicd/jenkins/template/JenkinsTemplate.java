package com.wenge.tbase.cicd.jenkins.template;


import com.wenge.tbase.cicd.entity.dto.JenkinsTemplateDTO;

/**
 * @ClassName: JenkinsTemplate
 * @Description: JenkinsTemplate
 * @Author: Wang XingPeng
 * @Date: 2020/12/2 10:54
 */
public class JenkinsTemplate {

    public static String getJenkinsTemplateXml(JenkinsTemplateDTO templateDTO) {
        String xml = "<flow-definition plugin=\"workflow-job@2.40\">\n" +
                        "  <actions>\n" +
                        "    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobAction plugin=\"pipeline-model-definition@1.7.2\"/>\n" +
                        "    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction plugin=\"pipeline-model-definition@1.7.2\">\n" +
                        "      <jobProperties/>\n" +
                        "      <triggers/>\n" +
                        "      <parameters/>\n" +
                        "      <options/>\n" +
                        "    </org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction>\n" +
                        "  </actions>\n" +
                        "  <description></description>\n" +
                        "  <keepDependencies>false</keepDependencies>\n" +
                        "  <properties>\n" +
                        "    <com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty plugin=\"gitlab-plugin@1.5.13\">\n" +
                        "      <gitLabConnection></gitLabConnection>\n" +
                        "    </com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty>\n" +
                        "  </properties>\n" +
                        "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2.86\">\n" +
                        "    <script>" + templateDTO.getScript() +  "</script>\n" +
                        "    <sandbox>true</sandbox>\n" +
                        "  </definition>\n" +
                        "  <triggers/>\n" +
                        "  <authToken>" + templateDTO.getToken() + "</authToken>\n" +
                        "  <disabled>false</disabled>\n" +
                        "</flow-definition>";
        return xml;
    }
}
