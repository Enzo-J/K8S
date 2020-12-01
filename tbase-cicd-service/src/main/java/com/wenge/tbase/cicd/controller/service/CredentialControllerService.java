package com.wenge.tbase.cicd.controller.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.wenge.tbase.cicd.entity.CicdCredential;
import com.wenge.tbase.cicd.entity.JenkinsNameValue;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateCredentialParam;
import com.wenge.tbase.cicd.entity.vo.GetCredentialListVo;
import com.wenge.tbase.cicd.service.CicdCredentialService;
import com.wenge.tbase.commons.entity.PageParam;
import com.wenge.tbase.commons.result.ListVo;
import com.wenge.tbase.commons.utils.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: CredentialControllerService
 * @Description: CredentialControllerService
 * @Author: Wang XingPeng
 * @Date: 2020/11/27 10:17
 */
@Component
@Slf4j
public class CredentialControllerService {

    @Resource
    private CicdCredentialService credentialService;

    @Resource
    private JenkinsHttpClient jenkinsHttpClient;

    private static final String CREDENTIAL_URL = "/credentials/store/system/domain/_/";

    /**
     * 根据名称查询凭证列表
     */
    public ListVo getCredentialList(String username, Integer current, Integer size) {
        QueryWrapper<CicdCredential> wrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(username)) {
            wrapper.like("username", username);
        }
        Page page = new Page(current, size);
        IPage list = credentialService.page(page, wrapper);
        ListVo listVo = new ListVo();
        List<CicdCredential> records = list.getRecords();
        List<GetCredentialListVo> listVos = new ArrayList<>();
        records.stream().forEach(o -> {
            GetCredentialListVo vo = new GetCredentialListVo();
            BeanUtil.copyProperties(o, vo);
            listVos.add(vo);
        });
        listVo.setTotal(list.getTotal());
        listVo.setDataList(listVos);
        return listVo;
    }

    /**
     * 添加凭证
     */
    public Boolean createCredential(CreateAndUpdateCredentialParam param) {
        String url = CREDENTIAL_URL + "createCredentials";
        CicdCredential cicdCredential = new CicdCredential();
        cicdCredential.setCredentialId(UUID.randomUUID().toString());
        cicdCredential.setUsername(param.getUsername());
        cicdCredential.setDescription(param.getDescription());
        String json = null;
        if (param.getType() == 1) {
            json = "{\"\": \"0\", " +
                    "\"credentials\": {" +
                    "\"scope\": \"GLOBAL\", " +
                    "\"username\": \"" + param.getUsername() + "\", " +
                    "\"password\": \"" + param.getPassword() + "\", " +
                    "\"$redact\": \"password\", " +
                    "\"id\": \"" + cicdCredential.getCredentialId() + "\", " +
                    "\"description\": \"" + param.getDescription() + "\", " +
                    "\"stapler-class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\", " +
                    "\"$class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\" }}";
            cicdCredential.setType(param.getType());
            cicdCredential.setPassword(AESUtils.encryptHex(param.getPassword()));
        } else if (param.getType() == 2) {
            json = "{\"\": \"2\", " +
                    "\"credentials\": {" +
                    "\"scope\": \"GLOBAL\", " +
                    "\"id\": \"" + cicdCredential.getCredentialId() + "\", " +
                    "\"description\": \"" + param.getDescription() + "\"," +
                    " \"username\": \"" + param.getUsername() + "\", " +
                    "\"privateKeySource\": {" +
                    "\"value\": \"0\", " +
                    "\"privateKey\": \"" + param.getPrivateKey() + "\", " +
                    "\"stapler-class\": \"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey$DirectEntryPrivateKeySource\", " +
                    "\"$class\": \"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey$DirectEntryPrivateKeySource\"}, " +
                    "\"passphrase\": \"\", " +
                    "\"$redact\": \"passphrase\", " +
                    "\"stapler-class\": \"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey\", " +
                    "\"$class\": \"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey\"}}";
            cicdCredential.setType(param.getType());
            cicdCredential.setPrivateKey(param.getPrivateKey());
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        JenkinsNameValue jenkinsNameValue = new JenkinsNameValue(json);
        nameValuePairs.add(jenkinsNameValue);

        try {
            // 写到jenkins里
            jenkinsHttpClient.post_form_with_result(url, nameValuePairs, true);
            // 写到数据库里
            return credentialService.save(cicdCredential);
        } catch (IOException e) {
            log.error("添加凭证错误", e.toString());
            return false;
        }
    }

    /**
     * 修改凭证
     */
    public Boolean updateCredential(CreateAndUpdateCredentialParam param) {
        String url = CREDENTIAL_URL + "credential/" + param.getCredentialId() + "/updateSubmit";
        String json = null;
        CicdCredential cicdCredential = new CicdCredential();
        if (StringUtils.isNotEmpty(param.getPassword())) {
            param.setPassword(AESUtils.encryptHex(param.getPassword()));
        }
        BeanUtil.copyProperties(param, cicdCredential);
        if (param.getType() == 1) {
            json = "{\"scope\": \"GLOBAL\", " +
                    "\"username\": \"" + param.getUsername() + "\", " +
                    "\"password\": \"" + param.getPassword() + "\", " +
                    "\"$redact\": \"password\", " +
                    "\"id\": \"" + param.getCredentialId() + "\", " +
                    "\"description\": \"" + param.getDescription() + "\"," +
                    "\"stapler-class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\"}";
        } else if (param.getType() == 2) {
            json = "{\"scope\": \"GLOBAL\", " +
                    "\"id\": \"" + param.getCredentialId() + "\", " +
                    "\"description\": \"" + param.getDescription() + "\", " +
                    "\"username\": \"" + param.getUsername() + "\", " +
                    "\"privateKeySource\": {" +
                    "\"value\": \"0\", " +
                    "\"privateKey\": \"" + param.getPassword() + "\", " +
                    "\"stapler-class\": \"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey$DirectEntryPrivateKeySource\", " +
                    "\"$class\": \"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey$DirectEntryPrivateKeySource\"}, " +
                    "\"passphrase\": \"\", " +
                    "\"$redact\": \"passphrase\"," +
                    "\"stapler-class\": \"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey\"}";
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        JenkinsNameValue jenkinsNameValue = new JenkinsNameValue(json);
        nameValuePairs.add(jenkinsNameValue);
        try {
            jenkinsHttpClient.post_form_with_result(url, nameValuePairs, true);
            credentialService.updateById(cicdCredential);
            return true;
        } catch (IOException e) {
            log.error("修改凭证错误", e.toString());
            return false;
        }
    }

    /**
     * 删除凭证
     */
    public Boolean deleteCredential(String credentialId) {
        String url = CREDENTIAL_URL + "credential/" + credentialId + "/doDelete";
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new JenkinsNameValue(null));
        try {
            jenkinsHttpClient.post_form_with_result(url, nameValuePairs, true);
            QueryWrapper<CicdCredential> wrapper = new QueryWrapper();
            wrapper.eq("credential_id", credentialId);
           return credentialService.remove(wrapper);
        } catch (IOException e) {
            log.error("删除凭证错误", e.toString());
            return false;
        }
    }
}
