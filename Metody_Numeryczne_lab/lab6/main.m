load('energy.mat');
close all;
[country, source, degrees, y_original, y_approximation, mse] = zadanie1(energy);
close all;
[country, source, degrees, y_original, y_movmean, y_approximation, mse] = zadanie2(energy);