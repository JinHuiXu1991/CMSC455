package homework2;

//CMSC 455 Homework2
//Jin Hui Xu

//least_square_fit.java  tailorable code, provide your input and setup 

//The purpose of this package is to provide a reliable and convenient
//means for fitting existing data by a few coefficients. The companion
//package check_fit provides the means to use the coefficients for
//interpolation and limited extrapolation.
//
//This package implements the least square fit. 
//
//The problem is stated as follows :
//Given measured data for values of Y based on values of X1,X2 and X3. e.g.
//
//Y_actual         X1      X2     X3  
//--------       -----   -----  -----
//32.5           1.0     2.5    3.7 
// 7.2           2.0     2.5    3.6 
// 6.9           3.0     2.7    3.5 
//22.4           2.2     2.1    3.1 
//10.4           1.5     2.0    2.6 
//11.3           1.6     2.0    3.1 
//
//Find a, b and c such that   Y_approximate =  a * X1 + b * X2 + c * X3
//and such that the sum of (Y_actual - Y_approximate) squared is minimized.
//
//The method for determining the coefficients a, b and c follows directly
//form the problem definition and mathematical analysis. (See more below)
//
//Y is called the dependent variable and X1 .. Xn the independent variables.
//The procedures below implements a few special cases and the general case.
//The number of independent variables can vary.
//The approximation equation may use powers of the independent variables
//The user may create additional independent variables e.g. X2 = SIN(X1)
//with the restriction that the independent variables are linearly
//independent.  e.g.  Xi not equal  p Xj + q  for all i,j,p,q
//
//
//
//The mathematical derivation of the least square fit is as follows :
//
//Given data for the independent variable Y in terms of the dependent
//variables S,T,U and V  consider that there exists a function F
//such that     Y = F(S,T,U,V)
//The problem is to find coefficients a,b,c and d such that
//       Y_approximate = a * S + b * T + c * U + d * V
//and such that the sum of ( Y - Y_approximate ) squared is minimized.
//
//Note: a, b, c, d are scalars. S, T, U, V, Y, Y_approximate are vectors.
//
//To find the minimum of  SUM( Y - Y_approximate ) ** 2
//the derivatives must be taken with respect to a,b,c and d and
//all must equal zero simultaneously. The steps follow :
//
//SUM( Y - Y_approximate ) ** 2 = SUM( Y - a*S - b*T - c*U - d*V ) ** 2
//
//d/da =  -2 * S * SUM( Y - A*S - B*T - C*U - D*V )
//d/db =  -2 * T * SUM( Y - A*S - B*T - C*U - D*V )
//d/dc =  -2 * U * SUM( Y - A*S - B*T - C*U - D*V )
//d/dd =  -2 * V * SUM( Y - A*S - B*T - C*U - D*V )
//
//Setting each of the above equal to zero and putting constant term on left
//the -2 is factored out,
//the independent variable is moved inside the summation
//
//SUM( a*S*S + b*S*T + c*S*U + d*S*V = S*Y )
//SUM( a*T*S + b*T*T + c*T*U + d*T*V = T*Y )
//SUM( a*U*S + b*U*T + c*U*U + d*U*V = U*Y )
//SUM( a*V*S + b*V*T + c*V*U + d*V*V = V*Y )
//
//Distributing the SUM inside yields
//
//a * SUM(S*S) + b * SUM(S*T) + c * SUM(S*U) + d * SUM(S*V) = SUM(S*Y)
//a * SUM(T*S) + b * SUM(T*T) + c * SUM(T*U) + d * SUM(T*V) = SUM(T*Y)
//a * SUM(U*S) + b * SUM(U*T) + c * SUM(U*U) + d * SUM(U*V) = SUM(U*Y)
//a * SUM(V*S) + b * SUM(V*T) + c * SUM(V*U) + d * SUM(V*V) = SUM(V*Y)
//
//To find the coefficients a,b,c and d solve the linear system of equations
//
//| SUM(S*S)  SUM(S*T)  SUM(S*U)  SUM(S*V) |   | a |   | SUM(S*Y) |
//| SUM(T*S)  SUM(T*T)  SUM(T*U)  SUM(T*V) | x | b | = | SUM(T*Y) |
//| SUM(U*S)  SUM(U*T)  SUM(U*U)  SUM(U*V) |   | c |   | SUM(U*Y) |
//| SUM(V*S)  SUM(V*T)  SUM(V*U)  SUM(V*V) |   | d |   | SUM(V*Y) |
//
//Some observations :
//S,T,U and V must be linearly independent.
//There must be more data sets (Y, S, T, U, V) than variables.
//The analysis did not depend on the number of independent variables
//A polynomial fit results from the substitutions S=1, T=X, U=X**2, V=X**3
//The general case for any order polynomial follows, fit_pn.
//Any substitution such as three variables to various powers may be used.

public class homework2
{
    double rms_err, avg_err, max_err;
    int idata = 0; // reset after each use of the data set
    
    homework2()
    {
    int n;

System.out.println("homework2.java");

// sample polynomial least square fit, 3th power 
{
	for(int i = 3; i < 18; i++)
	{
	    n=i+1; // need constant term and three powers 1,2,3 
	    double A[][] = new double[n][n];
	    double C[] = new double[n];
	    double Y[] = new double[n];
	    System.out.println("fit data to "+(n-1)+
			       " degree polynomial");
	    fit_pn(n, A, Y, C);
	    check_pn(n, C);
	    System.out.println("max_err="+max_err+", avg_err="+avg_err+
			       ", rms_err="+rms_err);
	    System.out.println(" ");
	}
}



    } // end least_square_fit
    
    int data_set(double yx[]) // returns 1 for data, 0 for end  
    {                                  // sets value of y for value of x 
	int k = 19; // 19
	double x1 = 0.0;
	double dx1 = 0.0314; // approx Pi 
	double xx;
	double yy;
	double[] time = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9};
	double[] thrust = {0.0, 6.0, 13.0, 5.0, 4.7, 4.4, 4.5, 4.6, 4.4, 4.5, 4.3, 4.4, 4.3, 4.4, 4.2, 4.3, 4.4, 4.2, 4.4, 0.0};
	
	idata++;  
	
	if(idata>k) {idata=0; return 0;} // ready for check 
	
	xx=time[idata];
	yy=thrust[idata];
	
	yx[1] = xx;
	yx[0] = yy;
	return 1;
    } // end data_set 
    

    void fit_pn(int n, double A[][], double Y[], double C[])
    {          // n is number of coefficients, highest power-1
	int i, j, k;
	double x, y, t;
	double yx[] = new double[2];
	double pwr[] = new double[n];
	
	for(i=0; i<n; i++)
	{
		for(j=0; j<n; j++)
		{
			A[i][j] = 0.0;
		}
		Y[i] = 0.0;
	}
	while(data_set(yx)==1)
	{
		y = yx[0];
		x = yx[1];
		pwr[0] = 1.0;
		for(i=1; i<n; i++) pwr[i] = pwr[i-1]*x;
		for(i=0; i<n; i++)
		{
			for(j=0; j<n; j++)
			{
				A[i][j] = A[i][j] + pwr[i]*pwr[j];
			}
			Y[i] = Y[i] + y*pwr[i];
		}
	}
	simeq(A, Y, C);
	//for(i=0; i<n; i++) System.out.println("C["+i+"]="+C[i]);
    } // end fit_pn 
    
    void check_pn(int n, double C[])
    {
	double x, y, ya, diff;
	double yx[] = new double[2];
	double sumsq = 0.0;
	double sum = 0.0;
	double maxe = 0.0;
	double xmin = 0.0;
	double xmax = 0.0;
	double ymin = 0.0;
	double ymax = 0.0;
	double xbad = 0.0;
	double ybad = 0.0;
	int i, k, imax=0;
	
	k = 0;
	while(data_set(yx)==1)
	{
		y = yx[0];
		x = yx[1];
		if(k==0)
		{
			xmin=x;
			xmax=x;
			ymin=y;
			ymax=y;
			imax=0;
			xbad=x;
			ybad=y;
		}
		if(x>xmax) xmax=x;
		if(x<xmin) xmin=x;
		if(y>ymax) ymax=y;
		if(y<ymin) ymin=y;
		k++;
		ya = C[n-1]*x;
		for(i=n-2; i>0; i--)
		{
			ya = (C[i]+ya)*x;
		}
		ya = ya + C[0];
		diff = Math.abs(y-ya);
		if(diff>maxe)
		{
			maxe=diff;
			imax=k;
			xbad=x;
			ybad=y;
		}
		sum = sum + diff;
		sumsq = sumsq + diff*diff;
	}
	//System.out.println("check_pn k="+k+", xmin="+xmin+", xmax="+xmax+
	//                   ", ymin="+ymin+", ymax="+ymax);
	max_err = maxe;
	avg_err = sum/(double)k;
	rms_err = Math.sqrt(sumsq/(double)k);
	//System.out.println("max="+max_err+" at i="+imax+", xbad="+xbad+
	//                   ", ybad="+ybad);
    } // end check_pn 
    
    void simeq(final double A[][], final double Y[], double X[])
    {
	// solve real linear equations for X where Y = A * X
	// method: Gauss-Jordan elimination using maximum pivot
	// usage:  simeq(A,Y,X);
	//    Translated to java by : Jon Squire , 26 March 2003
	//    First written by Jon Squire December 1959 for IBM 650, translated to
	//    other languages  e.g. Fortran converted to Ada converted to C
	//    then converted to java
	int n=A.length;
	int m=n+1;
	double B[][]=new double[n][m];  // working matrix
	int row[]=new int[n];           // row interchange indices
	int hold , I_pivot;             // pivot indices
	double pivot;                   // pivot element value
	double abs_pivot;
	if(A[0].length!=n || Y.length!=n || X.length!=n)
	{
		System.out.println("Error in Matrix.solve, inconsistent array sizes.");
	}
	// build working data structure
	for(int i=0; i<n; i++)
	{
		for(int j=0; j<n; j++)
		{
			B[i][j] = A[i][j];
		}
		B[i][n] = Y[i];
	}
	// set up row interchange vectors
	for(int k=0; k<n; k++)
	{
		row[k] = k;
	}
	//  begin main reduction loop
	for(int k=0; k<n; k++)
	{
		// find largest element for pivot
		pivot = B[row[k]][k];
		abs_pivot = Math.abs(pivot);
		I_pivot = k;
		for(int i=k; i<n; i++)
		{
			if(Math.abs(B[row[i]][k]) > abs_pivot)
			 {
				I_pivot = i;
				pivot = B[row[i]][k];
				abs_pivot = Math.abs(pivot);
			}
		}
		// have pivot, interchange row indices
		hold = row[k];
		row[k] = row[I_pivot];
		row[I_pivot] = hold;
		// check for near singular
		if(abs_pivot < 1.0E-10)
		{
			for(int j=k+1; j<n+1; j++)
			{
				B[row[k]][j] = 0.0;
			}
			System.out.println("redundant row (singular) "+row[k]);
		} // singular, delete row
		else
		{
			// reduce about pivot
			for(int j=k+1; j<n+1; j++)
			{
				B[row[k]][j] = B[row[k]][j] / B[row[k]][k];
			}
			//  inner reduction loop
			for(int i=0; i<n; i++)
			{
				if( i != k)
				{
					for(int j=k+1; j<n+1; j++)
					{
						B[row[i]][j] = B[row[i]][j] - B[row[i]][k] * B[row[k]][j];
					}
				}
			}
		}
		//  finished inner reduction
	}
	//  end main reduction loop
	//  build  X  for return, unscrambling rows
	for(int i=0; i<n; i++)
	{
		X[i] = B[row[i]][n];
	}
    } // end simeq 
    
    public static void main (String[] args)
    {
	new homework2();
    } // end main
    
} // end class least_square_fit 