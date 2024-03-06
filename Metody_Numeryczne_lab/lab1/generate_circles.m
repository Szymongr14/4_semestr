function [circles, index_number] = generate_circles(a, r_max, n_max)
    % The index_number is set to 193141 as per the instructions.
    index_number = 193141;
    
    % Calculate L1 as the last digit of the index_number.
    L1 = mod(index_number, 10);
    
    % Initialize circles matrix based on whether L1 is even or odd.
    if mod(L1, 2) == 0
        % If L1 is even, the matrix has n_max rows and 3 columns.
        circles = zeros(n_max, 3);
    else
        % If L1 is odd, the matrix has 3 rows and n_max columns.
        circles = zeros(3, n_max);
    end
    
    % Ensure r_max does not exceed half the length of the square's side.
    if r_max > a/2
        error('r_max cannot exceed half the length of the square''s side.');
    end
    
    % Calculate the minimum distance between centers based on r_max and a.
    min_distance = 2 * r_max;
    
    % Generate circles ensuring they do not overlap.
    for i = 1:n_max
        % Generate random positions for the center of the circle.
        x = rand() * (a - r_max);
        y = rand() * (a - r_max);
        
        % Check if the new circle overlaps with any of the previously generated circles.
        overlap = false;
        for j = 1:i-1
            % Calculate the distance between the new circle's center and the j-th circle's center.
            distance = sqrt((x - circles(j, 1))^2 + (y - circles(j, 2))^2);
            
            % If the distance is less than the sum of the radii, the circles overlap.
            if distance < (r_max + circles(j, 3))
                overlap = true;
                break;
            end
        end
        
        % If there's no overlap, add the new circle to the matrix.
        if ~overlap
            if mod(L1, 2) == 0
                circles(i, :) = [x, y, r_max];
            else
                circles(:, i) = [r_max, x, y];
            end
        else
            % If there's an overlap, regenerate the circle's position.
            i = i - 1; % Decrement i to retry the current iteration.
        end
    end
end
