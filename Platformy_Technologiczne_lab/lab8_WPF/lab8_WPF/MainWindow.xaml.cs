using System.Collections.Specialized;
using System.IO;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Forms;
using Microsoft.Win32;
using System.Windows.Controls;
using MessageBox = System.Windows.Forms.MessageBox;


namespace lab8_WPF;

/// <summary>
/// Interaction logic for MainWindow.xaml
/// </summary>
public partial class MainWindow : Window
{
    public MainWindow()
    {
        InitializeComponent();
        var root = RecursiveSearch("C:\\Users\\szymo\\Documents\\GitHub\\4_semestr\\Metody_Numeryczne_lab");
        MainTreeView.Visibility = Visibility.Visible;
        MainTreeView.Items.Add(root);
    }

    private void MiOpen_OnClick(object sender, RoutedEventArgs e)
    {
        Console.WriteLine("Open clicked !");
        var dlg = new FolderBrowserDialog() { Description = "Select directory to open"};
        var result = dlg.ShowDialog();
        if (result == System.Windows.Forms.DialogResult.OK)
        {
            var rootPath = dlg.SelectedPath;
            var root = RecursiveSearch(rootPath);
            MainTreeView.Visibility = Visibility.Visible;
            MainTreeView.Items.Add(root);
        }
        else if (result != System.Windows.Forms.DialogResult.Cancel)
        {
            MessageBox.Show("Try again to choose a folder", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }
    }
    
    private TreeViewItem RecursiveSearch(string currentDir)
    {
        var root = new TreeViewItem
        {
            Header = new DirectoryInfo(currentDir).Name,
            ContextMenu = (ContextMenu)FindResource("FolderContext"),
            Tag = currentDir
        };

        foreach (var directory in Directory.GetDirectories(currentDir))
        {
            var directoryItem = RecursiveSearch(directory);
            root.Items.Add(directoryItem);
        }

        foreach (var file in Directory.GetFiles(currentDir))
        {
            var fileItem = new TreeViewItem
            {
                Header = new FileInfo(file).Name,
                Tag = file,
                ContextMenu = (ContextMenu)FindResource("FileContext")
            };
            root.Items.Add(fileItem);
        }
        return root;
    }
    
    private void MiExit_OnClick(object sender, RoutedEventArgs e)
    {
        Close();
    }

    private void MainTreeView_OnSelectedItemChanged(object sender, RoutedPropertyChangedEventArgs<object> e)
    {
        
        var treeViewItem = (TreeViewItem)e.NewValue;
        if (treeViewItem.Tag == null) return; // it means file has been deleted
        var filePath = treeViewItem.Tag.ToString();
        MyTextBox.Visibility = Visibility.Hidden;
    }

    private void DeleteItem_Click(object sender, RoutedEventArgs e)
    {
        var menuItem = (MenuItem)sender;
        var contextMenu = (ContextMenu)menuItem.Parent;
        var treeViewItem = (TreeViewItem)contextMenu.PlacementTarget;

        var filePath = treeViewItem.Tag.ToString();

        if (File.GetAttributes(filePath!).HasFlag(FileAttributes.ReadOnly))
        {
            File.SetAttributes(filePath!, File.GetAttributes(filePath!) & ~FileAttributes.ReadOnly);
        }

        if (File.GetAttributes(filePath!).HasFlag(FileAttributes.Directory))
        {
            Directory.Delete(filePath!, true);
        }
        else
        {
            File.Delete(filePath!);
        }

        var parent = (TreeViewItem)treeViewItem.Parent;
        parent.Items.Remove(treeViewItem);
    }

    private void OpenFile_Click(object sender, RoutedEventArgs e)
    {
        var menuItem = (MenuItem)sender;
        var contextMenu = (ContextMenu)menuItem.Parent;
        var treeViewItem = (TreeViewItem)contextMenu.PlacementTarget;

        var filePath = treeViewItem.Tag.ToString();
        Console.WriteLine(filePath);
        // Implement your file opening logic here
        if (treeViewItem.Tag == null) return; // it means file has been deleted
        try
        {
            var fileContent = File.ReadAllText(filePath!);
            MyTextBox.Visibility = Visibility.Visible;
            MyTextBox.Text = fileContent;
        }
        catch (Exception ex)
        {
            MessageBox.Show($"Could not read file: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }
    }

    private void AddFilesToFolder_Click(object sender, RoutedEventArgs e)
    {
        var menuItem = (MenuItem)sender;
        var contextMenu = (ContextMenu)menuItem.Parent;
        var treeViewItem = (TreeViewItem)contextMenu.PlacementTarget;

        var folderPath = treeViewItem.Tag.ToString();
        Console.WriteLine(folderPath);
        // Implement your file adding logic here
    }
}