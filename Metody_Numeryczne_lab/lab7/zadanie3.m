function [integration_error, Nt, ft_5, integral_1000] = zadanie3()
    % Numeryczne całkowanie metodą Simpsona.
    % Nt - wektor zawierający liczby podprzedziałów całkowania
    % integration_error - integration_error(1,i) zawiera błąd całkowania wyznaczony
    %   dla liczby podprzedziałów równej Nt(i). Zakładając, że obliczona wartość całki
    %   dla Nt(i) liczby podprzedziałów całkowania wyniosła integration_result,
    %   to integration_error(1,i) = abs(integration_result - reference_value),
    %   gdzie reference_value jest wartością referencyjną całki.
    % ft_5 - gęstość funkcji prawdopodobieństwa dla n=5
    % integral_1000 - całka od 0 do 5 funkcji gęstości prawdopodobieństwa
    %   dla 1000 podprzedziałów całkowania


    reference_value = 0.0473612919396179; % wartość referencyjna całki

    Nt = 5:50:10^4;
    integration_error = zeros(1, length(Nt));
    for i = 1:length(Nt)
        N = Nt(i);
        integration_result = simpson(@f, N);
        integration_error(i) = abs(integration_result - reference_value);
    end
    ft_5 = f(5);
    integral_1000 = simpson(@f, 1000);

    figure();

    loglog(Nt,integration_error);
    title("Zaleznosc błędu całkowania od liczby przedzialow");
    xlabel("Liczba przedzialow calkowania");
    ylabel("Blad calkowania");
end

function integral = simpson(function_, N)
    a = 0;
    b = 5;
    
    delta_x = (b - a) / N;
    
    sum = 0;
    for i = 1:N
        xi = a + (i - 1) * delta_x;
        xip1 = a + i * delta_x;
        
        sum = sum + function_(xi) + 4*function_(0.5 * (xi + xip1)) + function_(xip1);
    end
    integral = sum * delta_x / 6;
end

function y = f(t)
    sigma = 3;
    mi = 10;

    y = 1 / (sigma * sqrt(2 * pi)) * exp(-(t-mi)^2 / (2 * sigma^2));
end