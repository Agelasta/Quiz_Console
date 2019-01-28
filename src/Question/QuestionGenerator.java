package Question;

public class QuestionGenerator extends Question {

    public static Question createQuestion(String question, String option1, String option2, String option3, int pointer) {

        Question result = new Question();

        result.setQuestion(question);
        result.setOption1(option1);
        result.setOption2(option2);
        result.setOption3(option3);
        result.setPointer(pointer);

        return result;
    }
}


