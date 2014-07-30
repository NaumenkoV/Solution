package com.softserveinc.ita.interviewfactory.dao.mock;

import com.softserveinc.ita.dao.InterviewDAO;
import com.softserveinc.ita.dao.QuestionsBlockDAO;
import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestionsBlockDAOMock implements QuestionsBlockDAO {

    public static List<QuestionsBlock> allQuestionsBlocks = new ArrayList();

    @Autowired
    InterviewDAO interviewDAO;

    //Standard questions mock

    private QuestionsBlock standardQuestionsBlock = new QuestionsBlock();{
        QuestionInformation questionInformation1 = new QuestionInformation();
        questionInformation1.setQuestion("What is your name?");
        questionInformation1.setWeight(2);
        questionInformation1.setId("0");
        questionInformation1.setId("Hz, standart vopros");
        QuestionInformation questionInformation2 = new QuestionInformation();
        questionInformation2.setQuestion("How are you?");
        questionInformation2.setWeight(3);
        questionInformation2.setId("1");
        questionInformation2.setId("Hz, standart vopros");

        Set<QuestionInformation> QuestionInformationList = new HashSet<QuestionInformation>();{
            QuestionInformationList.add(questionInformation1);
            QuestionInformationList.add(questionInformation2);
        }

        standardQuestionsBlock.setQuestions(QuestionInformationList);
        standardQuestionsBlock.setId("Hz, standart vopros");
    }

    @Override
    public QuestionsBlock getQuestionsBlockByInterviewIdAndUserId(String userID, String appointmentId) {


        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionsBlock.setQuestions(questionInformationsList);
        questionsBlock.setInterviewId("1");
        questionsBlock.setUserId("1");
        questionsBlock.setId("1");

        allQuestionsBlocks.add(questionsBlock);

        for (QuestionsBlock questionsBlock1 : allQuestionsBlocks){
            if (questionsBlock1.getInterviewId().equals(appointmentId) && questionsBlock1.getUserId().equals(userID))
                allQuestionsBlocks.clear();
                return questionsBlock1;
        }
        return null;
    }

    @Override
    public QuestionsBlock getQuestionsBlockFromInterviewByQuestionsBlockId(String questionsBlockId) {

        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();

        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionsBlock.setQuestions(questionInformationsList);
        questionsBlock.setInterviewId("1");
        questionsBlock.setUserId("1");
        questionsBlock.setId("1");
        allQuestionsBlocks.clear();
        allQuestionsBlocks.add(questionsBlock);

        for (QuestionsBlock questionsBlock1 : allQuestionsBlocks){
            if (questionsBlock1.getId().equals(questionsBlockId))
                return questionsBlock1;
        }
        return null;
    }

    @Override
    public String updateQuestionsBlock(QuestionsBlock newQuestionsBlock) {

        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionsBlock.setQuestions(questionInformationsList);
        questionsBlock.setInterviewId("1");
        questionsBlock.setUserId("1");
        questionsBlock.setId("1");
        allQuestionsBlocks.add(questionsBlock);

        for (int i = 0; i < allQuestionsBlocks.size(); i++){
            if (allQuestionsBlocks.get(i).getId().equals(newQuestionsBlock.getId())){
                allQuestionsBlocks.add(i, newQuestionsBlock);
                return newQuestionsBlock.getId();
            }
        }
        return null;
    }

    @Override
    public void deleteQuestionsBlockById(String questionsBlockId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QuestionsBlock getStandardQuestionsBlockFromInterview(String interviewId) {
                Set<QuestionsBlock> questionsBlocks = interviewDAO.getInterviewByAppointmentId(interviewId).getQuestionsBlocks();

        for (QuestionsBlock questionsBlock1 : questionsBlocks){
            if(questionsBlock1.getUserId() == null)
                return questionsBlock1;
        }
        return null;
    }

    @Override
    public QuestionsBlock getStandardQuestionsBlock() {
        return standardQuestionsBlock;
    }

    @Override
    public void setStandardQuestionsBlock(QuestionsBlock standardQuestionsBlock) {
        this.standardQuestionsBlock = standardQuestionsBlock;
    }
}
