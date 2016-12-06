//CMSC 455 Homework 3
//Jin Hui Xu
//compile   g++ -o homework3 homework3.cpp 
//run       homework3 > homework3.out


#include <iostream>
#include <iomanip>
#include <math.h> 

using namespace std;

#undef  abs
#define abs(x) ((x)<0.0?(-(x)):(x))

void problem1a()
{
	int n = 16;
	double area = 0.0;
	double acutualValue = 1.0 - cos(1.0); //exact solution
	double h, value, error;

	//print the title of terms
	cout << "problem 1a:" << endl;
	cout << left << setw(19) << "number of points" << setw(29) 
		<< "integration computed value" << setw(15) << "actual value"
		<< setw(10) << "error" << endl;

	//loop through 4 types of points
	for(n; n <= 128; n = n * 2)
	{
		h = 1.0/(double)n;

		//get the sum of area
		for(int i = 1; i < n - 1; i++)
		{
			area += sin(i*h);
		}
		value = h * ((sin(0) + sin(1.0)) / 2 + area);     //calculate the integration value
		error = value - acutualValue;			  //calculate the error

		//print the output
		cout << left << setw(19) << n 
			<< setw(29) << value  
			<< setw(15) << acutualValue 
			<< setw(10) << error << endl;

		area = 0.0; //reset area value
	}

}


void gaulegf(double x1, double x2, double x[], double w[], int n)
{
	int i, j, m;
	double eps = 3.0E-14;
	double p1, p2, p3, pp, xl, xm, z, z1;
  
	m = (n+1)/2;
	xm = 0.5*(x2+x1);
	xl = 0.5*(x2-x1);
	for(i=1; i<=m; i++)
  	{
		z = cos(3.141592654*((double)i-0.25)/((double)n+0.5));
		while(1)
    		{
     			p1 = 1.0;
			p2 = 0.0;
      			for(j=1; j<=n; j++)
      			{
				p3 = p2;
				p2 = p1;
				p1 = ((2.0*(double)j-1.0)*z*p2-((double)j-1.0)*p3)/
      	        		(double)j;
      			}
      			pp = (double)n*(z*p1-p2)/(z*z-1.0);
     			z1 = z;
     			z = z1 - p1/pp;
			if(abs(z-z1) <= eps) break;
    		}
    		x[i] = xm - xl*z;
   		x[n+1-i] = xm + xl*z;
    		w[i] = 2.0*xl/((1.0-z*z)*pp*pp);
    		w[n+1-i] = w[i];
  	}
} /* end gaulegf */


static double f(double p)
{
	return p*p;
}


void problem1b()
{
	int n = 8;
  	double x[17];
  	double w[17];
  	double area, error;
 	double actualArea = 1.0 - cos(1.0);
  	int i;

	//print the title of terms
	cout << "\n\nproblem 1b:" << endl;
	cout << left << setw(19) << "number of points" << setw(29) 
		<< "integration computed value" << setw(15) << "actual value"
		<< setw(10) << "error" << endl;
	
	for(n; n <= 16; n = n * 2)
	{
		gaulegf(0.0, 1.0, x, w, n);
		area = 0.0; //reset area

		for(i=1; i<=n; i++)
		{
			area += w[i] * sin(x[i]);
		}

		error = area - actualArea;	//calculate the error

		//print the output
		cout << left << setw(19) << n 
			<< setw(29) << area  
			<< setw(15) << actualArea
			<< setw(10) << error << endl;
	}
}


void problem2()
{
	int count = 0;
	double grid[4] = {0.1, 0.01, 0.001, 0.0001};
	double area;
	
	cout << "\n\nproblem 2:" << endl;

	//loop through all 4 types of grid
	for(int i = 0; i < 4; i++)
	{
		//loop through the x axle
		for(double x = -3.0; x < 3.0; x += grid[i])					
		{
			//loop through the y axle
			for(double y = -4.0; y < 4.0; y += grid[i])
			{
			        if((x - 2) * (x - 2) + (y - 2) * (y - 2) > 1 * 1 &&  //circle 1
				    x * x + (y - 2) * (y - 2) < 2 * 2 &&             //circle 2
				    x * x + y * y < 3 * 3)                           //circle 3
				        //add counter if the point meets all requirements
				        count++; 
			}
		}
		//multiply count by the area of a square
		area = count * grid[i] * grid[i];
		cout << left << "Area with grid " << setw(6) << grid[i] << " = "<< area << endl;
		count = 0;	//reset counter
	}
}


int main(int argc, char * argv[]) 
{
	problem1a();
	problem1b();
	problem2();
	
	return 0;
}