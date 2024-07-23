package catchweak.web.config.morpheme

import catchweak.web.morpheme.tasklet.MorphemeAnalysisTasklet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
@EnableBatchProcessing
@EnableScheduling
class BatchConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val articleTasklet: MorphemeAnalysisTasklet
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun job(): Job {
        return JobBuilder("morphemeAnalysisJob", jobRepository)
            .start(step1())
            .build()
    }

    @Bean
    fun step1(): Step {
        return StepBuilder("morphemeAnalysisStep", jobRepository)
            .tasklet(articleTasklet, transactionManager)
            .build()
    }

    @Bean
    fun jobLauncher(): JobLauncher {
        val jobLauncher = TaskExecutorJobLauncher()
        jobLauncher.setJobRepository(jobRepository)
        jobLauncher.setTaskExecutor(taskExecutor())
        jobLauncher.afterPropertiesSet()
        return jobLauncher
    }

    @Bean
    fun taskExecutor(): ThreadPoolTaskExecutor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.corePoolSize = 1  // 1개의 스레드 사용
        taskExecutor.maxPoolSize = 1
        taskExecutor.queueCapacity = 10
        taskExecutor.setThreadNamePrefix("morphemeAnalysisTask-")
        taskExecutor.initialize()
        return taskExecutor
    }

    @Scheduled(cron = "0 */10 * * * ?")  // 10분마다 실행
    fun performJob() {
        val jobParameters = JobParametersBuilder()
            .addDate("launchDate", Date())
            .toJobParameters()

        logger.info("Starting morpheme analysis job")
        jobLauncher().run(job(), jobParameters)
    }
}