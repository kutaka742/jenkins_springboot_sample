#!/usr/bin/env groovy

pipeline {

    agent{
        label 'master'
    }

    //オプション
    options {
        //同時ビルド無効
        disableConcurrentBuilds()
    }

    stages{
        //チェックアウト
        stage('Checkout'){
            steps{
                script{
                    //クリーン
                    cleanWs()
                    //チェックアウト
                    checkout scm

                    //pom.xml情報取得
                    groupId = getGroupIdFromPom("pom.xml")
                    artifactId = getArtifactIdFromPom("pom.xml")
                    version = getVersionFromPom("pom.xml")

                    //初期設定
                    currentBuild.result = 'SUCCESS'
                }
            }
        }

        //ビルド
        stage('Build'){
            steps{
                script{
                    //ビルド(テストスキップ)
                    bat "mvn package -Dmaven.test.skip=true"

                    //成果物保管
                    stash name: 'release-ap', includes: '**/target/*.war'
                }
            }
        }

        //テスト
        stage('Test'){
            steps{
                script{
                    //テスト実行
                    try {
                        //テスト実行
                        bat "mvn org.jacoco:jacoco-maven-plugin:prepare-agent test"
                    } catch (Exception e) {
                        //テストは失敗してもビルドの流れを止めないため結果コードを不安定とする
                        currentBuild.result = 'UNSTABLE'
                        print "Test failure resultCode =[${currentBuild.result}]"
                        echo e.toString()
                    }
                }
            }
        }

        //デプロイ
        stage('Deploy'){
            steps{
                script{
                    //作業前プロセス
                    bat "jps -l"
                    //AP起動(起動したらタスクマネージャ等でjava.exeをkillする必要がある)
                    bat "start java -jar ./target/demo-1.0.0-RELEASE.war"
                    //作業後プロセス
                    bat "jps -l"
                }
            }
        }
    }

    post{
        always{
            //成果物保存
            archiveArtifacts artifacts: "**/target/*.war" , fingerprint: true
        }
        failure{
            echo "エラー"
        }
        success{
            echo "正常"
        }
        unstable{
            echo "不安定"
        }
    }
}


// pom.xml情報取得
def getVersionFromPom(pom) {
    def matcher = readFile(pom) =~ '<version>(.+)</version>'
    matcher ? matcher[0][1] : null
}
def getGroupIdFromPom(pom) {
    def matcher = readFile(pom) =~ '<groupId>(.+)</groupId>'
    matcher ? matcher[0][1] : null
}
def getArtifactIdFromPom(pom) {
    def matcher = readFile(pom) =~ '<artifactId>(.+)</artifactId>'
    matcher ? matcher[0][1] : null
}
