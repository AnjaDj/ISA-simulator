using Arhitektura_Projekat_2;
using System;
using System.Diagnostics;

namespace Arhitektura_Projekat_2
{
    public class Program
    {
        public static void Main(string[] args)
        {
            string inputFile = "huge.jpg"; ;
            string outputFile = "huge_sh.jpg";
            
            if (args.Length == 2)
            {
                inputFile = args[0];
                outputFile = args[1];
            }
            Console.WriteLine($"inputFile -->{inputFile}");
            Console.WriteLine($"outputFile -->{outputFile}");
            ImageSharpener is1 = new();

            Stopwatch s1 = Stopwatch.StartNew();
            is1.BaseAlgorithm(inputFile, outputFile);
            s1.Stop();
            Console.WriteLine($"Base {s1.Elapsed.TotalMilliseconds}");

            s1 = Stopwatch.StartNew();
            is1.ParallelAlgorithm(inputFile, outputFile);
            s1.Stop();
            Console.WriteLine($"Parallel {s1.Elapsed.TotalMilliseconds}");

            s1 = Stopwatch.StartNew();
            is1.CacheAlgorithm(inputFile, outputFile);
            s1.Stop();
            Console.WriteLine($"Cache {s1.Elapsed.TotalMilliseconds}");

            s1 = Stopwatch.StartNew();
            is1.ParallelCacheAlgorithm(inputFile, outputFile);
            s1.Stop();
            Console.WriteLine($"Parallel Cache {s1.Elapsed.TotalMilliseconds}");

        }
    }
}