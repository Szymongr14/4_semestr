function plot_direct(N,vtime_direct)
    plot(N, vtime_direct);
    xlabel('Matrix Size');
    ylabel('Computation Time');
    title('Computation time to size of a matrix');
    print('zad2.png','-dpng')
end