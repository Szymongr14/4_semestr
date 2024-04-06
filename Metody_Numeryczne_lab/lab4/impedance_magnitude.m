function impedance_delta = impedance_magnitude(omega)

R = 525;
C = 7e-5;
L = 3;
M = 75; % docelowa wartość modułu impedancji

if omega <=0 
    error("error: omega is lower/equal to zero")
end




Z = 1/(sqrt((1/R^2)+((omega * C) - (1/(omega*L)))^2));


impedance_delta = Z - M;

end

