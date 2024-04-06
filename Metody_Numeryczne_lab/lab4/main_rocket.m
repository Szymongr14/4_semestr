a = 1;
b = 50;
ytolerance = 1e-12;
max_iterations = 100;
time = 10 ;
velocity_delta = rocket_velocity(time);

[time_bisection, ysolution_bisection, iterations_bisection, xtab_bisection, xdif_bisection] = bisection_method(a, b, max_iterations, ytolerance, @rocket_velocity);
[time_secant, ysolution_secant, iterations_secant, xtab_secant, xdif_secant] = secant_method(a, b, max_iterations, ytolerance, @rocket_velocity);

figure;

subplot(2,1,1);
plot(1:length(xtab_bisection), xtab_bisection, 'r', 1:length(xtab_secant), xtab_secant, 'b');
xlabel('Iteracja');
ylabel('Przybliżenie czasu [s]');
title('Przybliżenia czasu dla metod bisekcji i siecznych');
legend('Bisekcja', 'Sieczna');

subplot(2,1,2);
semilogy(1:length(xdif_bisection), xdif_bisection, 'r', 1:length(xdif_secant), xdif_secant, 'b');
xlabel('Iteracja');
ylabel('Różnica pomiędzy przybliżeniami [s]');
title('Różnice pomiędzy przybliżeniami czasu dla metod bisekcji i siecznych');
legend('Bisekcja', 'Sieczna');
