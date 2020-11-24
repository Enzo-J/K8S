//git凭证ID
def git_auth="81885ec4-3e3d-49fe-a9c4-b03aef9064ba"
//git地址
def git_url="git@172.16.0.6:lh-economics/app-manage-platform.git"
//tag标签版本
def tag="latest"
//harbor地址
def harbor_url="https://172.16.0.11:80"
//harbor项目名称
def harbor_project_name="app-manage-platform"
//harbor凭证id
def harbor_auth_id="9683d4d5-9ab6-473c-bae0-65c8b1bd52cd"


node {
    stage('拉取代码') {
        checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
    }
    stage('检查代码') {
        def scannerHome = tool 'sonar-scanner'
        withSonarQubeEnv('sonar') {
            sh """
                cd ${project_name}/src/main/sonar
                ${scannerHome}/bin/sonar-scanner
            """
        }
    }
    stage('编译,安装公共子工程') {
        sh "mvn -f tbase-commons clean install"
    }
    stage('编译,打包,部署工程') {
        sh "mvn -f ${project_name} clean package dockerfile:build "
        def imageName = "${project_name}:${tag}"
        //给镜像打标签
        sh "docker tag ${imageName} ${harbor_url}/${harbor_project_name}/${imageName}"
        //把镜像推送到harbor
        withCredentials([usernamePassword(credentialsId: "${harbor_auth_id}", passwordVariable: 'password', usernameVariable: 'username')]) {
            //登录harbor
            sh "docker login -u ${username} -p ${password} ${harbor_url}"
            //上传镜像
            sh "docker push ${harbor_url}/${harbor_project_name}/${imageName}"
        }
        //删除本地镜像
        sh "docker rmi -f ${imageName}"
        sh "docker rmi -f ${harbor_url}/${harbor_project_name}/${imageName}"
        //sshPublisher(publishers: [sshPublisherDesc(configName: 'master_server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "/opt/jenkins_shell/deploy.sh $harbor_url $harbor_project_name $project_name $tag $port", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
    }

}