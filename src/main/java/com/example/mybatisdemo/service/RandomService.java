package com.example.mybatisdemo.service;

import java.util.Date;
import java.util.Random;

public class RandomService {
    public static void main(String[] args) {
        int data[]=new int[]{1,42,51,62,8,94,23,13,40,5};
        show(data);
        /*permuteBySort(data);
        show(data);*/
        randomizeInPlace(data);
        show(data);
    }

    private static void show(int[] data) {
        System.out.println("========================");
        for(int i = 0; i < data.length; i++)
        {
            System.out.print(data[i] + " ");
        }
        System.out.println();
        System.out.println("========================");
    }

    private static void permuteBySort(int[] data)
    {
        int len=data.length;
        int len3=len*len*len;
        int P[]=getRandom(1,len3,len);

//冒泡排序
        for(int i=len-1; i>0; i--)
        {
            for(int j=0; j<i ; j++)
            {
                if(P[j]>P[j+1])
                {
                    int temp=data[j];
                    data[j]=data[j+1];
                    data[j+1]=temp;

                    temp=P[j];
                    P[j]=P[j+1];
                    P[j+1]=temp;
                }
            }
        }
    }

    private static int[] getRandom(int a,int b,int n) {
        if(a>b)
        {
            int temp=a;
            a=b;
            b=temp;
        }

        Date dt=new Date();
        Random random=new Random(dt.getSeconds());
        int res[]=new int[n];
        for(int i=0; i<n; i++)
        {
            res[i]=(int)(random.nextDouble()*(Math.abs(b-a)+1))+a;
        }
        return res;
    }


    private static void randomizeInPlace(int[] data)
    {
        Random random=new Random();
        int len=data.length;
        for(int i=0; i<len; i++)
        {
            double m = random.nextDouble();
            System.out.println(m);
            int pos=(int)(m*(len-i+1)+i)-1;
            int temp=data[i];
            data[i]=data[pos];
            data[pos]=temp;
        }
    }
}
