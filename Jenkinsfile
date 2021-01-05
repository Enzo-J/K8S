//git凭证ID
def git_auth="81885ec4-3e3d-49fe-a9c4-b03aef9064ba"
//git地址
def git_url="git@172.16.0.6:lh-economics/app-manage-platform.git"
//tag标签版本
def tag="latest"
//harbor地址
def harbor_url="172.16.0.11"
//harbor项目名称
def harbor_project_name="app-manage-platform"
//harbor凭证id
def harbor_auth_id="9683d4d5-9ab6-473c-bae0-65c8b1bd52cd"

node {
    //把选择的项目信息转为数组
    def selectedProjects = "${project_name}".split(',')

    stage('拉取代码') {
        checkout([$class: 'GitSCM', branches: [[name: "*/${branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
    }
    stage('检查代码') {
        def scannerHome = tool 'sonar-scanner'
        withSonarQubeEnv('sonar') {
            for(int i=0;i<selectedProjects.size();i++){
                //取出每个项目的名称和端口
                def currentProject = selectedProjects[i];
                //项目名称
                def currentProjectName = currentProject.split('@')[0]
                //项目启动端口
                def currentProjectPort = currentProject.split('@')[1]
                sh """
                    cd ${currentProjectName}/src/main/sonar
                    ${scannerHome}/bin/sonar-scanner
                """
                echo "${currentProjectName}完成代码审查"
            }
        }
    }
    stage('编译,安装公共子工程') {
        sh "mvn -f tbase-commons clean install"
    }
    stage('编译,打包,推送镜像,构建镜像'){
        for(int i=0;i<selectedProjects.size();i++){
            //取出每个项目的名称和端口
            def currentProject = selectedProjects[i];
            //项目名称
            def currentProjectName = currentProject.split('@')[0]             
            //项目启动端口
            def currentProjectPort = currentProject.split('@')[1]
            //定义镜像名称
            def imageName = "${currentProjectName}:${tag}"
            //编译，构建本地镜像
            sh "mvn -f ${currentProjectName} clean package dockerfile:build "
           //判断名称是否包含父目录            
            if(currentProjectName.contains("/")){
               currentProjectName=currentProjectName.split("/")[currentProjectName.split("/").size()-1]
               imageName = "${currentProjectName}:${tag}"
            }
            //给镜像打标签
            sh "docker tag ${imageName} ${harbor_url}/${harbor_project_name}/${imageName} "
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
            echo "${currentProjectName}完成编译，构建镜像"
            //从仓库拉取镜像,自动生成镜像
            sshPublisher(publishers: [sshPublisherDesc(configName: 'master_server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "/opt/jenkins_shell/deploy.sh $harbor_url $harbor_project_name $currentProjectName $tag $currentProjectPort", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            echo "${currentProjectName}构建完成"
        }
    }
}