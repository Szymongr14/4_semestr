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
using Microsoft.Win32.SafeHandles;
using MessageBox = System.Windows.Forms.MessageBox;
using Path = System.IO.Path;


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
        var fileAttributes = File.GetAttributes(filePath!);
        Console.WriteLine(fileAttributes);
        var attributesString = new StringBuilder("----");
        if(fileAttributes == FileAttributes.Directory)
        {
            attributesString = new StringBuilder("directory");
        }
        else
        {
            if (fileAttributes.HasFlag(FileAttributes.ReadOnly)) attributesString[0] = 'r';
            if (fileAttributes.HasFlag(FileAttributes.Archive)) attributesString[1] = 'a';
            if (fileAttributes.HasFlag(FileAttributes.Hidden)) attributesString[2] = 'h';
            if (fileAttributes.HasFlag(FileAttributes.System)) attributesString[3] = 's';
        }
        
        MyTextBox.Visibility = Visibility.Hidden;
        StatusBarAttributes.Text = attributesString.ToString();
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

        var parentFolderPath = treeViewItem.Tag.ToString();
        var dialog = new InputDialog();
        var result = dialog.ShowDialog();

        if (result == true)
        {
            var fileName = dialog.FileName;
            var isFile = dialog.IsFile;
            var isReadOnly = dialog.IsReadOnly;
            var isArchive = dialog.IsArchive;
            var isHidden = dialog.IsHidden;
            var isSystem = dialog.IsSystem;
            var fullPath = fileName;

            if (parentFolderPath != null)
            {
                fullPath = Path.Combine(parentFolderPath, fileName);
            }

            if (isFile)
            {
                // Create file
                using (File.Create(fullPath)){}
                var fileAttributes = File.GetAttributes(fullPath);
                
                if (isReadOnly) fileAttributes |= FileAttributes.ReadOnly;
                if (isArchive) fileAttributes |= FileAttributes.Archive;
                if (isHidden) fileAttributes |= FileAttributes.Hidden;
                if (isSystem) fileAttributes |= FileAttributes.System;
                
                File.SetAttributes(fullPath, fileAttributes);
            }
            else
            {
                Directory.CreateDirectory(fullPath);
                var dirInfo = new DirectoryInfo(fullPath);
                var dirAttributes = dirInfo.Attributes;
                
                if (isReadOnly) dirAttributes |= FileAttributes.ReadOnly;
                if (isHidden) dirAttributes |= FileAttributes.Hidden;
                
                dirInfo.Attributes = dirAttributes;
            }

            var newItem = new TreeViewItem
            {
                Header = fileName,
                Tag = fullPath,
                ContextMenu = isFile
                    ? (ContextMenu)FindResource("FileContext")
                    : (ContextMenu)FindResource("FolderContext")
            };
            treeViewItem.Items.Add(newItem);
        }
    }
}
