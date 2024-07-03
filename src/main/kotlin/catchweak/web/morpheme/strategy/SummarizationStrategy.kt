package catchweak.web.morpheme.strategy

interface SummarizationStrategy {
    fun summarize(title: String, text: String, keywords:List<String>): String;
}