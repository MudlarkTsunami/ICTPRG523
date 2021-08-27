package SurveyManager;

import java.util.ArrayList;
import java.util.List;

public class CustomSort
{


    public static void BubbleSort(SurveyQuestionData[] sentData)
    {
        int j, temp;

        SurveyQuestionData holderData1, holderData2;
        boolean flag = true;

        while (flag)
        {
            flag = false;
            for (j = 0; j < sentData.length - 1; j++)
            {
                // Descending sort
                if (sentData[j].getQuestionInt() < sentData[j + 1].getQuestionInt())
                {
//                    temp = sentData[j].getQuestionInt();
//                    sentData[j].setQuestionInt(sentData[j + 1].getQuestionInt());
//                    sentData[j + 1].setQuestionInt(temp);

                    holderData1 = sentData[j];
                    holderData2 = sentData[j+1];

                    sentData[j] = holderData2;
                    sentData[j+1] = holderData1;
                    flag = true;
                }
            }
        }
    }
    public static void BubbleSortAsc(SurveyQuestionData[] sentData)
    {
        int j;
        SurveyQuestionData holderData1, holderData2;
        boolean flag = true;

        while (flag)
        {
            flag = false;
            for (j = 0; j < sentData.length - 1; j++)
            {
                // Descending sort
                if (sentData[j].getQuestionInt() > sentData[j + 1].getQuestionInt())
                {
                    holderData1 = sentData[j];
                    holderData2 = sentData[j+1];

                    sentData[j] = holderData2;
                    sentData[j+1] = holderData1;
                    flag = true;
                    flag = true;
                }
            }
        }
    }




    public static void InsertionSort(SurveyQuestionData[] sentData)
    {

        int j;                     // the number of items sorted so far
        SurveyQuestionData key;                // the item to be inserted
        int i;

        for (j = 1; j < sentData.length; j++)    // Start with 1 (not 0)
        {
            key = sentData[j];

            for (i = j - 1; (i >= 0) && ((sentData[i].getTopic().toUpperCase().charAt(0)) > (key.getTopic().toUpperCase().charAt(0))); i--)   // Smaller values are moving up
            {
                sentData[i + 1] = sentData[i];
            }
            sentData[i + 1] = key;    // Put the key in its proper location
        }

    }

    public static void SelectionSort(SurveyQuestionData[] sentData)
    {

        int i, j, first;
        SurveyQuestionData temp;
        for ( i = sentData.length - 1; i > 0; i -- )
        {
            first = 0;   //initialize to subscript of first element
            for(j = 1; j <= i; j ++)   //locate the smallest element between positions 1 and i.
            {

                String temp1 = sentData[j].getQuestionBody();
                String temp2 = (sentData[first].getQuestionBody());
                if( temp1.compareToIgnoreCase(temp2) > 0)
                {
                    first = j;
                }
            }
            temp = sentData[first];   //swap smallest found with element in position i.
            sentData[first] = sentData[i];
            sentData[i] = temp;
        }


//        int i, j, first, temp;
//        for ( i = num.length - 1; i > 0; i - - )
//        {
//            first = 0;   //initialize to subscript of first element
//            for(j = 1; j <= i; j ++)   //locate smallest element between positions 1 and i.
//            {
//                if( num[ j ] < num[ first ] )
//                    first = j;
//            }
//            temp = num[ first ];   //swap smallest found with element in position i.
//            num[ first ] = num[ i ];
//            num[ i ] = temp;
//        }

    }
}

