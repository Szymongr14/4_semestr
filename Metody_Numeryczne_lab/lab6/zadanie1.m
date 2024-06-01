function [country, source, degrees, y_original, y_approximation, mse] = zadanie1(energy)
% Głównym celem tej funkcji jest wyznaczenie aproksymacji danych o produkcji energii elektrycznej w wybranym kraju i z wybranego źródła energii.
% Wybór kraju i źródła energii należy określić poprzez nadanie w tej funkcji wartości zmiennym typu string: country, source.
% Dopuszczalne wartości tych zmiennych można sprawdzić poprzez sprawdzenie zawartości struktury energy zapisanej w pliku energy.mat.
% 
% energy - struktura danych wczytana z pliku energy.mat
% country - [String] nazwa kraju
% source  - [String] źródło energii
% degrees - wektor zawierający cztery stopnie wielomianu dla których wyznaczono aproksymację
% y_original - dane wejściowe, czyli pomiary produkcji energii zawarte w wektorze energy.(country).(source).EnergyProduction
% y_approximation - tablica komórkowa przechowująca cztery wartości funkcji aproksymującej dane wejściowe. y_approximation stanowi aproksymację stopnia degrees(i).
% mse - wektor o rozmiarze 4x1: mse(i) zawiera wartość błędu średniokwadratowego obliczonego dla aproksymacji stopnia degrees(i).

country = 'Sweden';
source = 'Solar';
degrees = [1,10,20,30];
y_original = energy.(country).(source).EnergyProduction;
y_approximation= [];
mse = [];

% Sprawdzenie dostępności danych
if isfield(energy, country) && isfield(energy.(country), source)
    % Przygotowanie danych do aproksymacji
    y_original = energy.(country).(source).EnergyProduction;
    dates = energy.(country).(source).Dates;

    x = linspace(-1,1,length(y_original))';

    % Pętla po wielomianach różnych stopni
    for i = 1:length(degrees)
        p = polyfit(x, y_original, degrees(i));
        y_approximation{i} = polyval(p, x);
        squared_errors = (y_original - y_approximation{i}).^2;
        mse(i) = mean(squared_errors);
    end

else
    disp(['Dane dla (country=', country, ') oraz (source=', source, ') nie są dostępne.']);
end

subplot(2,1,1);
hold on;
plot(x, y_original);
for i = 1:length(degrees)
    plot(x, y_approximation{i});
end

 title('Solar energy production for Brazil');
 xlabel('x');
 ylabel('y');
 legend('oryginalny (75 nodes)', '1', '10', '20', '30', 'Location', 'best');
 hold off

subplot(2,1,2);

bar(mse); 
title('Mean square error');  
set(gca, 'XTickLabel', degrees);
xlabel('polynomial degrees'); 
ylabel('MSE value');
print -dpng zadanie1.png 


end