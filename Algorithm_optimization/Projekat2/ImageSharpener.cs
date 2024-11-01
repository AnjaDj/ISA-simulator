using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Net.Mime.MediaTypeNames;

namespace Arhitektura_Projekat_2
{
    public class ImageSharpener
    {
        private static readonly int[,] kernel = new int[,] { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };

        public void BaseAlgorithm(string inputFile, string outputFile)
        {
            using Stream stream = File.Open(inputFile, FileMode.Open);
            Bitmap original = new(stream);
            Bitmap bSrc = (Bitmap)original.Clone();
            BitmapData bmData = original.LockBits(new Rectangle(0, 0, original.Width, original.Height),
                               ImageLockMode.ReadWrite, original.PixelFormat);
            BitmapData bmSrc = bSrc.LockBits(new Rectangle(0, 0, bSrc.Width, bSrc.Height),
                               ImageLockMode.ReadWrite, bSrc.PixelFormat);
            int stride = bmData.Stride;
            int stride2 = stride * 2;

            IntPtr Scan0 = bmData.Scan0; // Pokazivac na pocetak slike (originala)
            IntPtr SrcScan0 = bmSrc.Scan0; // -||- (kopije)

            int bytesPerPixel = System.Drawing.Image.GetPixelFormatSize(original.PixelFormat) / 8;
            int nOffset = stride - original.Width * bytesPerPixel;
            int nWidth = original.Width - 2;
            int nHeight = original.Height - 2;

            for (int x = 0; x < nWidth; x++)
            {
                unsafe
                {
                    byte* p = (byte*)(void*)Scan0 + x * (nOffset + nHeight * bytesPerPixel);
                    byte* pSrc = (byte*)(void*)SrcScan0 + x * (nOffset + nHeight * bytesPerPixel);
                    for (int y = 0; y < nHeight; y++)
                    {
                        int nPixelR = (pSrc[2] * kernel[0, 0]) +
                            (pSrc[5] * kernel[0, 1]) +
                            (pSrc[8] * kernel[0, 2]) +
                            (pSrc[2 + stride] * kernel[1, 0]) +
                            (pSrc[5 + stride] * kernel[1, 1]) +
                            (pSrc[8 + stride] * kernel[1, 2]) +
                            (pSrc[2 + stride2] * kernel[2, 0]) +
                            (pSrc[5 + stride2] * kernel[2, 1]) +
                            (pSrc[8 + stride2] * kernel[2, 2]);

                        if (nPixelR < 0) nPixelR = 0;
                        if (nPixelR > 255) nPixelR = 255;
                        p[5 + stride] = (byte)nPixelR;

                        int nPixelG = (pSrc[1] * kernel[0, 0]) +
                            (pSrc[4] * kernel[0, 1]) +
                            (pSrc[7] * kernel[0, 2]) +
                            (pSrc[1 + stride] * kernel[1, 0]) +
                            (pSrc[4 + stride] * kernel[1, 1]) +
                            (pSrc[7 + stride] * kernel[1, 2]) +
                            (pSrc[1 + stride2] * kernel[2, 0]) +
                            (pSrc[4 + stride2] * kernel[2, 1]) +
                            (pSrc[7 + stride2] * kernel[2, 2]);

                        if (nPixelG < 0) nPixelG = 0;
                        if (nPixelG > 255) nPixelG = 255;
                        p[4 + stride] = (byte)nPixelG;

                        int nPixelB = (pSrc[0] * kernel[0, 0]) +
                                       (pSrc[3] * kernel[0, 1]) +
                                       (pSrc[6] * kernel[0, 2]) +
                                       (pSrc[0 + stride] * kernel[1, 0]) +
                                       (pSrc[3 + stride] * kernel[1, 1]) +
                                       (pSrc[6 + stride] * kernel[1, 2]) +
                                       (pSrc[0 + stride2] * kernel[2, 0]) +
                                       (pSrc[3 + stride2] * kernel[2, 1]) +
                                       (pSrc[6 + stride2] * kernel[2, 2]);

                        if (nPixelB < 0) nPixelB = 0;
                        if (nPixelB > 255) nPixelB = 255;
                        p[3 + stride] = (byte)nPixelB;

                        p += bytesPerPixel;
                        pSrc += bytesPerPixel;
                    }
                }
            }

            original.UnlockBits(bmData);
            bSrc.UnlockBits(bmSrc);
            original.Save(outputFile);
        }

        public void ParallelAlgorithm(string inputFile, string outputFile)
        {
            using Stream stream = File.Open(inputFile, FileMode.Open);
            Bitmap original = new(stream);
            Bitmap bSrc = (Bitmap)original.Clone();
            BitmapData bmData = original.LockBits(new Rectangle(0, 0, original.Width, original.Height),
                               ImageLockMode.ReadWrite, original.PixelFormat);
            BitmapData bmSrc = bSrc.LockBits(new Rectangle(0, 0, bSrc.Width, bSrc.Height),
                               ImageLockMode.ReadWrite, bSrc.PixelFormat);
            int stride = bmData.Stride;
            int stride2 = stride * 2;

            IntPtr Scan0 = bmData.Scan0; // Pokazivac na pocetak slike (originala)
            IntPtr SrcScan0 = bmSrc.Scan0; // -||- (kopije)

            int bytesPerPixel = System.Drawing.Image.GetPixelFormatSize(original.PixelFormat) / 8;
            int nOffset = stride - original.Width * bytesPerPixel;
            int nWidth = original.Width - 2;
            int nHeight = original.Height - 2;

            Parallel.For(0, nWidth, new ParallelOptions() { MaxDegreeOfParallelism = Environment.ProcessorCount }, x =>
            {
                unsafe
                {
                    byte* p = (byte*)(void*)Scan0 + x * (nOffset + nHeight * bytesPerPixel);
                    byte* pSrc = (byte*)(void*)SrcScan0 + x * (nOffset + nHeight * bytesPerPixel);
                    for (int y = 0; y < nHeight; y++)
                    {
                        int nPixelR = (pSrc[2] * kernel[0, 0]) +
                            (pSrc[5] * kernel[0, 1]) +
                            (pSrc[8] * kernel[0, 2]) +
                            (pSrc[2 + stride] * kernel[1, 0]) +
                            (pSrc[5 + stride] * kernel[1, 1]) +
                            (pSrc[8 + stride] * kernel[1, 2]) +
                            (pSrc[2 + stride2] * kernel[2, 0]) +
                            (pSrc[5 + stride2] * kernel[2, 1]) +
                            (pSrc[8 + stride2] * kernel[2, 2]);

                        if (nPixelR < 0) nPixelR = 0;
                        if (nPixelR > 255) nPixelR = 255;
                        p[5 + stride] = (byte)nPixelR;

                        int nPixelG = (pSrc[1] * kernel[0, 0]) +
                            (pSrc[4] * kernel[0, 1]) +
                            (pSrc[7] * kernel[0, 2]) +
                            (pSrc[1 + stride] * kernel[1, 0]) +
                            (pSrc[4 + stride] * kernel[1, 1]) +
                            (pSrc[7 + stride] * kernel[1, 2]) +
                            (pSrc[1 + stride2] * kernel[2, 0]) +
                            (pSrc[4 + stride2] * kernel[2, 1]) +
                            (pSrc[7 + stride2] * kernel[2, 2]);

                        if (nPixelG < 0) nPixelG = 0;
                        if (nPixelG > 255) nPixelG = 255;
                        p[4 + stride] = (byte)nPixelG;

                        int nPixelB = (pSrc[0] * kernel[0, 0]) +
                                       (pSrc[3] * kernel[0, 1]) +
                                       (pSrc[6] * kernel[0, 2]) +
                                       (pSrc[0 + stride] * kernel[1, 0]) +
                                       (pSrc[3 + stride] * kernel[1, 1]) +
                                       (pSrc[6 + stride] * kernel[1, 2]) +
                                       (pSrc[0 + stride2] * kernel[2, 0]) +
                                       (pSrc[3 + stride2] * kernel[2, 1]) +
                                       (pSrc[6 + stride2] * kernel[2, 2]);

                        if (nPixelB < 0) nPixelB = 0;
                        if (nPixelB > 255) nPixelB = 255;
                        p[3 + stride] = (byte)nPixelB;

                        p += bytesPerPixel;
                        pSrc += bytesPerPixel;
                    }
                }
            });

            original.UnlockBits(bmData);
            bSrc.UnlockBits(bmSrc);
            original.Save(outputFile);
        }

        public void CacheAlgorithm(string inputFile, string outputFile)
        {
            using Stream stream = File.Open(inputFile, FileMode.Open);
            Bitmap original = new(stream);
            Bitmap bSrc = (Bitmap)original.Clone();
            BitmapData bmData = original.LockBits(new Rectangle(0, 0, original.Width, original.Height),
                               ImageLockMode.ReadWrite, original.PixelFormat);
            BitmapData bmSrc = bSrc.LockBits(new Rectangle(0, 0, bSrc.Width, bSrc.Height),
                               ImageLockMode.ReadWrite, bSrc.PixelFormat);
            int stride = bmData.Stride;
            int stride2 = stride * 2;

            IntPtr Scan0 = bmData.Scan0; // Pokazivac na pocetak slike (originala)
            IntPtr SrcScan0 = bmSrc.Scan0; // -||- (kopije)

            int bytesPerPixel = System.Drawing.Image.GetPixelFormatSize(original.PixelFormat) / 8;
            int nOffset = stride - original.Width * bytesPerPixel;
            int nWidth = original.Width - 2;
            int nHeight = original.Height - 2;

            for (int y = 0; y < nHeight; y++)
            {
                unsafe
                {
                    byte* p = (byte*)(void*)Scan0 + y * (nOffset + nWidth * bytesPerPixel);
                    byte* pSrc = (byte*)(void*)SrcScan0 + y * (nOffset + nWidth * bytesPerPixel);
                    for (int x = 0; x < nWidth; x++)
                    {
                        int nPixelR = (pSrc[2] * kernel[0, 0]) +
                            (pSrc[5] * kernel[0, 1]) +
                            (pSrc[8] * kernel[0, 2]) +
                            (pSrc[2 + stride] * kernel[1, 0]) +
                            (pSrc[5 + stride] * kernel[1, 1]) +
                            (pSrc[8 + stride] * kernel[1, 2]) +
                            (pSrc[2 + stride2] * kernel[2, 0]) +
                            (pSrc[5 + stride2] * kernel[2, 1]) +
                            (pSrc[8 + stride2] * kernel[2, 2]);

                        if (nPixelR < 0) nPixelR = 0;
                        if (nPixelR > 255) nPixelR = 255;
                        p[5 + stride] = (byte)nPixelR;

                        int nPixelG = (pSrc[1] * kernel[0, 0]) +
                            (pSrc[4] * kernel[0, 1]) +
                            (pSrc[7] * kernel[0, 2]) +
                            (pSrc[1 + stride] * kernel[1, 0]) +
                            (pSrc[4 + stride] * kernel[1, 1]) +
                            (pSrc[7 + stride] * kernel[1, 2]) +
                            (pSrc[1 + stride2] * kernel[2, 0]) +
                            (pSrc[4 + stride2] * kernel[2, 1]) +
                            (pSrc[7 + stride2] * kernel[2, 2]);

                        if (nPixelG < 0) nPixelG = 0;
                        if (nPixelG > 255) nPixelG = 255;
                        p[4 + stride] = (byte)nPixelG;

                        int nPixelB = (pSrc[0] * kernel[0, 0]) +
                                       (pSrc[3] * kernel[0, 1]) +
                                       (pSrc[6] * kernel[0, 2]) +
                                       (pSrc[0 + stride] * kernel[1, 0]) +
                                       (pSrc[3 + stride] * kernel[1, 1]) +
                                       (pSrc[6 + stride] * kernel[1, 2]) +
                                       (pSrc[0 + stride2] * kernel[2, 0]) +
                                       (pSrc[3 + stride2] * kernel[2, 1]) +
                                       (pSrc[6 + stride2] * kernel[2, 2]);

                        if (nPixelB < 0) nPixelB = 0;
                        if (nPixelB > 255) nPixelB = 255;
                        p[3 + stride] = (byte)nPixelB;

                        p += bytesPerPixel;
                        pSrc += bytesPerPixel;
                    }
                }
            }

            original.UnlockBits(bmData);
            bSrc.UnlockBits(bmSrc);
            original.Save(outputFile);
        }

        public void ParallelCacheAlgorithm(string inputFile, string outputFile)
        {
            using Stream stream = File.Open(inputFile, FileMode.Open);
            Bitmap original = new(stream);
            Bitmap bSrc = (Bitmap)original.Clone();
            BitmapData bmData = original.LockBits(new Rectangle(0, 0, original.Width, original.Height),
                               ImageLockMode.ReadWrite, original.PixelFormat);
            BitmapData bmSrc = bSrc.LockBits(new Rectangle(0, 0, bSrc.Width, bSrc.Height),
                               ImageLockMode.ReadWrite, bSrc.PixelFormat);
            int stride = bmData.Stride;
            int stride2 = stride * 2;

            IntPtr Scan0 = bmData.Scan0; // Pokazivac na pocetak slike (originala)
            IntPtr SrcScan0 = bmSrc.Scan0; // -||- (kopije)

            int bytesPerPixel = System.Drawing.Image.GetPixelFormatSize(original.PixelFormat) / 8;
            int nOffset = stride - original.Width * bytesPerPixel;
            int nWidth = original.Width - 2;
            int nHeight = original.Height - 2;

            Parallel.For(0, nHeight, new ParallelOptions() { MaxDegreeOfParallelism = Environment.ProcessorCount }, y =>
            {
                unsafe
                {
                    byte* p = (byte*)(void*)Scan0 + y * (nOffset + nWidth * bytesPerPixel);
                    byte* pSrc = (byte*)(void*)SrcScan0 + y * (nOffset + nWidth * bytesPerPixel);
                    for (int x = 0; x < nWidth; x++)
                    {
                        int nPixelR = (pSrc[2] * kernel[0, 0]) +
                            (pSrc[5] * kernel[0, 1]) +
                            (pSrc[8] * kernel[0, 2]) +
                            (pSrc[2 + stride] * kernel[1, 0]) +
                            (pSrc[5 + stride] * kernel[1, 1]) +
                            (pSrc[8 + stride] * kernel[1, 2]) +
                            (pSrc[2 + stride2] * kernel[2, 0]) +
                            (pSrc[5 + stride2] * kernel[2, 1]) +
                            (pSrc[8 + stride2] * kernel[2, 2]);

                        if (nPixelR < 0) nPixelR = 0;
                        if (nPixelR > 255) nPixelR = 255;
                        p[5 + stride] = (byte)nPixelR;

                        int nPixelG = (pSrc[1] * kernel[0, 0]) +
                            (pSrc[4] * kernel[0, 1]) +
                            (pSrc[7] * kernel[0, 2]) +
                            (pSrc[1 + stride] * kernel[1, 0]) +
                            (pSrc[4 + stride] * kernel[1, 1]) +
                            (pSrc[7 + stride] * kernel[1, 2]) +
                            (pSrc[1 + stride2] * kernel[2, 0]) +
                            (pSrc[4 + stride2] * kernel[2, 1]) +
                            (pSrc[7 + stride2] * kernel[2, 2]);

                        if (nPixelG < 0) nPixelG = 0;
                        if (nPixelG > 255) nPixelG = 255;
                        p[4 + stride] = (byte)nPixelG;

                        int nPixelB = (pSrc[0] * kernel[0, 0]) +
                                       (pSrc[3] * kernel[0, 1]) +
                                       (pSrc[6] * kernel[0, 2]) +
                                       (pSrc[0 + stride] * kernel[1, 0]) +
                                       (pSrc[3 + stride] * kernel[1, 1]) +
                                       (pSrc[6 + stride] * kernel[1, 2]) +
                                       (pSrc[0 + stride2] * kernel[2, 0]) +
                                       (pSrc[3 + stride2] * kernel[2, 1]) +
                                       (pSrc[6 + stride2] * kernel[2, 2]);

                        if (nPixelB < 0) nPixelB = 0;
                        if (nPixelB > 255) nPixelB = 255;
                        p[3 + stride] = (byte)nPixelB;

                        p += bytesPerPixel;
                        pSrc += bytesPerPixel;
                    }
                }
            });

            original.UnlockBits(bmData);
            bSrc.UnlockBits(bmSrc);
            original.Save(outputFile);
        }
    }
}