function [circles, index_number, circle_areas, rand_counts, counts_mean] = generate_circles(a, r_max, n_max)
    index_number = 193141;
    r_max = a/2;
    L1 = mod(index_number, 10);
    rand_counts = zeros(1,n_max);


    if mod(L1, 2) == 0
        circles = zeros(n_max, 3);
        i = 1;
        while i <= n_max
            circle_is_valid = 0;
            rand_count = 0;

            while ~circle_is_valid
                r = rand() * r_max;
                x = rand() * a;
                y = rand() * a;
                rand_count = rand_count+1;

                if x + r <= a && x - r >= 0 && y + r <= a && y - r >= 0
                    circle_is_overlap = 0;
                    for j=1:size(circles,1)
                        if sqrt((x - circles(j,1))^2 + (y - circles(j,2))^2) <= r+circles(j,3)
                            circle_is_overlap = 1;
                            break;
                        end
                    end
                
                    if ~circle_is_overlap
                        circle_is_valid = 1;
                    end
                end
            end

            circles(i, 1) = x;
            circles(i, 2) = y;
            circles(i, 3) = r;
            i = i+1;
            
        end
        
    else
        circles = zeros(3, n_max);
        circle_areas = zeros(1,n_max);
        counts_mean = zeros(1,n_max);
        i = 1;
        while i <= n_max
            circle_is_valid = 0;
            rand_count = 0;

            while ~circle_is_valid
                r = rand() * r_max;
                x = rand() * a;
                y = rand() * a;
                rand_count = rand_count+1;

                if x + r <= a && x - r >= 0 && y + r <= a && y - r >= 0
                    circle_is_overlap = 0;
                    for j=1:size(circles,2)
                        if sqrt((x - circles(2,j))^2 + (y - circles(3,j))^2) <= r+circles(1,j)
                            circle_is_overlap = 1;
                            break;
                        end
                    end
                
                    if ~circle_is_overlap
                        circle_is_valid = 1;
                    end
                end
            end

            circles(1, i) = r;
            circles(2, i) = x;
            circles(3, i) = y;

            circle_areas(i) = pi * r^2;
            rand_counts(i) = rand_count;
            
            counts_mean(i) = mean(rand_counts(1:i));

            i = i+1;
            
        end
    end
    circle_areas = cumsum(circle_areas);
end