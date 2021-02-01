package eu.musicnova.musicnova.boot

import eu.musicnova.api.MainThreadRunner
import eu.musicnova.api.Module
import eu.musicnova.musicnova.Const
import eu.musicnova.musicnova.MusicnovaApplication
import org.atteo.classindex.ClassIndex
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole
import org.springframework.beans.factory.getBean
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import picocli.CommandLine
import java.util.function.Consumer
import java.util.function.Function
import kotlin.system.exitProcess

@CommandLine.Command(
    name = ""
)
class MusicnovaCommandlineBoot : Runnable {

    override fun run() {
        println("Starting...")
        val modules = ClassIndex.getSubclasses(Module::class.java).toList().distinctBy { clazz -> clazz.kotlin.qualifiedName }
        println("Modules: [${modules.joinToString(",")}]")

        val springApp = SpringApplication( *modules.toTypedArray())
        springApp.setHeadless(true)
        springApp.setLogStartupInfo(true)
        springApp.setRegisterShutdownHook(true)
        springApp.setLazyInitialization(false)
        springApp.addInitializers(ApplicationContextInitializer<ConfigurableApplicationContext> { applicationContext ->
            applicationContext.beanFactory.registerSingleton(
                CommandLineBeanPresent::class.qualifiedName!!,
                CommandLineBeanPresent(this)
            )
        })
        springApp.run()
        MainThreadRunner.run()
    }

    companion object Static : Consumer<Array<String>> {
        override fun accept(args: Array<String>) {
            if (
                (System.getProperty("jansi.skip")?.toBoolean() != true)
                && !(System.getProperty("spring.output.ansi.enabled").equals("always", true))
            ) {
                AnsiConsole.systemInstall()
            }
            val commandLine = CommandLine(MusicnovaCommandlineBoot::class.java)
            exitProcess(commandLine.execute(*args))
        }


        private fun isIdeaCheck(): Boolean {
            return try {
                Class.forName("com.intellij.rt.execution.application.AppMain")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }


    }

}