package SurveyManager;

public class CustomSort
{


    public static SurveyQuestionData[] BubbleSort(SurveyQuestionData[] sentData)
    {
        int j, temp;
        boolean flag = true;

        while (flag)
        {
            flag = false;
            for (j = 0; j < sentData.length - 1; j++)
            {
                // Descending sort
                if (sentData[j].getQuestionInt() < sentData[j + 1].getQuestionInt())
                {
                    temp = sentData[j].getQuestionInt();
                    sentData[j].setQuestionInt(sentData[j + 1].getQuestionInt());
                    sentData[j + 1].setQuestionInt(temp);
                    flag = true;
                }
            }
        }
        return sentData;
    }




    public static SurveyQuestionData[] ShellSort(SurveyQuestionData[] sentData)
    {
        SurveyQuestionData[] dataReturning = sentData;
        return dataReturning;
    }

    public static SurveyQuestionData[] HeapSort(SurveyQuestionData[] sentData)
    {
        SurveyQuestionData[] dataReturning = sentData;
        return dataReturning;
    }
}

