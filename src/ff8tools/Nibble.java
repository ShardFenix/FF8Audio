/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff8tools;


public class Nibble
{
boolean[] bits={false,false,false,false};

public Nibble(int n)
    {
    bits[0]=(n%2!=0);
    n/=2;
    bits[1]=(n%2!=0);
    n/=2;
    bits[2]=(n%2!=0);
    n/=2;
    bits[3]=(n%2!=0);
    n/=2;
    }

public int getUnsignedValue()
    {
    int result=0;
    if (bits[3])result+=8;
    if (bits[2])result+=4;
    if (bits[1])result+=2;
    if (bits[0])result+=1;
    return result;
    }

public int getSignedValue()
    {
    int result=0;
    if (!bits[3])
        {
        if (bits[2])result+=4;
        if (bits[1])result+=2;
        if (bits[0])result+=1;
        }
    else
        {
        if (!bits[2])result-=4;
        if (!bits[1])result-=2;
        if (!bits[0])result-=1;
        result-=1;
        }
    return result;
    }

}