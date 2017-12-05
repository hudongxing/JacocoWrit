# JacocoWrit
1. 将三个java文件放入工程内
2. build.gradle中加入：
   apply plugin: 'jacoco'
   build.gradle中加入 ：
   buildTypes {
        debug {
            testCoverageEnabled = true
        }
        }
   build.gradle中加入：
   def coverageSourceDirs = [
        '../app/src/main/java'
]

task jacocoTestReport(type: JacocoReport) {
    group = "Reporting"
    description = "Generate Jacoco coverage reports after running tests."
    reports {
        xml.enabled = true
        html.enabled = true
    }
    classDirectories = fileTree(
            dir: './build/intermediates/classes/debug',
            excludes: ['**/R*.class',
                       '**/*$InjectAdapter.class',
                       '**/*$ModuleAdapter.class',
                       '**/*$ViewInjector*.class'
            ])
    sourceDirectories = files(coverageSourceDirs)
    executionData = files("$buildDir/outputs/code-coverage/connected/flavors/coverage.ec")

    doFirst {
        new File("$buildDir/intermediates/classes/").eachFileRecurse { file ->
            if (file.name.contains('$$')) {
                file.renameTo(file.path.replace('$$', '$'))
            }
        }
    }
}

 3.  AndroidManifest.xml中加入
    <instrumentation
        android:handleProfiling="true"
        android:label="CoverageInstrumentation"
        android:name=".test.JacocoInstrumentation"
        android:targetPackage="com.example.violet.hdxvideo">

    </instrumentation>
 4. 将app打包至待测手机（使用installDebug方式安装）
 5. 使用db shell am instrument com.xx.xx.xx/.xx.JacocoInstrumentation重新启动app
 6. 将手机内生成的coverage.ec拷贝至本地电脑
 7. 在gradle projects视图下，app->Tasks->verification->createDebugCoverageReport，双击createDebugCoverageReport，此番操作后会在app/build/outputs下生成code-coverage目录
 8. 将生成的coverage.ec（保存在sd卡中）文件放入app\build\outputs\code-coverage\connected目录中
 9. 在gradle projects视图下，app->Tasks->reporting->jacocoTestReport，双击jacocoTestReport生成代码覆盖率报告，生成成功后便可在build\reports\jacoco\jacocoTestReport\html文件夹下，打开index.html就可以查看了
