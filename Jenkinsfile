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
                    groupId = getGroupIdFromPom(".\\pom.xml")
                    artifactId = getArtifactIdFromPom(".\\pom.xml")
                    version = getVersionFromPom(".\\pom.xml")

                    //初期設定
                    currentBuild.result = 'SUCCESS'
                }
            }
        }

        //ビルド
        stage ('Build'){
            steps{
                script{
                    //ビルド
                    bat "mvn package -Dmaven.test.skip=true"

                    //成果物保管
                    stash name: 'release-ap', includes: '**/target/*.jar'
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
                        bat "mvn test"
                    } catch (Exception e) {
                        //テストは失敗してもビルドの流れを止めないため結果コードを不安定とする
                        currentBuild.result = 'UNSTABLE'
                        print "Test failure resultCode =[${currentBuild.result}]"
                        echo e.toString()
                    }

                    //JUnitレポート
                    junit '**/surefire-reports/*.xml'
                }
            }
        }

        //デプロイ
        stage ('Deploy'){
            steps{
                script{
                    bat "start javaw -Xmx257M -jar -Dfile.encoding=utf-8 ./target/demo1-0.0.1-SNAPSHOT.jar"
                }
            }
        }
    }

    post{
        always{
            //成果物保存
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        failure{
            echo "エラー"
        }
        success{
            echo "正常"
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
