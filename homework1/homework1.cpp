//CMSC 455 Homework 1
//Jin Hui Xu
//compile   g++ -o homework1 homework1.cpp 
//run       homework1 > homework1.out

#include <iostream>
#include <iomanip>
using namespace std;

int main(int argc, char* argv[]) 
{
	int index = 1;
	double FdBody, FdFins, Fg, Ft, dv, ds;
	double thrust[] = {0, 6, 13, 5, 4.7, 4.4, 4.5, 4.6, 4.4, 4.5, 4.3, 
			   4.4, 4.3, 4.4, 4.2, 4.3, 4.4, 4.2, 4.4, 0, 0, 0, 0, 0,
			   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0, 0, 
			   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	double t = 0.0;
	double s = 0;
	double v = 0;
	double a = 0;
	double F = 0;
	double m = 0.0340 + 0.0242;
	double g = 9.80665;
	double CdBody = 0.45;
	double CdFins = 0.01;
	double Rho = 1.293;
	double bodyArea = 0.000506;
	double FinsArea = 0.00496;
	double dt = 0.1;

	//print the column title
	cout << left << setw(10) << "Time(s)" 
	     << setw(12) << "Height(m)" 
	     << setw(16) << "Velocity(m/s)" 
	     << setw(22) << "Acceleration(m/s^2)" 
	     << setw(11) << "Force(N)" 
	     << setw(10) << "Mass(kg)" << endl;

	//loop until the velocity goes negative (the rocket is coming down)
	while(v >= 0)
	{
		//print the result for every second
		cout << left << setw(10) << t 
		     << setw(12) << s 
		     << setw(16) << v 
		     << setw(22) << a 
		     << setw(11) << F 
		     << setw(10) << m << endl;

		FdBody = CdBody * Rho * bodyArea * v * v / 2;
		FdFins = CdFins * Rho * FinsArea * v * v / 2;
		Fg = m * g;
		Ft = thrust[index];
		F = Ft - (FdBody + FdFins + Fg);
		a = F / m;
		dv = a * dt;
		v = v + dv;
		ds = v * dt;
		s = s + ds;
		m = m - 0.0001644 * Ft;
		t = t + dt;
		index++;

	}
	return 0;
}
