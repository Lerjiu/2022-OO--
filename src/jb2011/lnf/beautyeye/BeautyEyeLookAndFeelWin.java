/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BeautyEyeLookAndFeelWin.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package jb2011.lnf.beautyeye;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.InsetsUIResource;

import jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;
import jb2011.lnf.beautyeye.winlnfutils.WinUtils;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * <p>
 * BeautyEye Swing外观实现方案 - Windows平台专用外观实现主类.<br>
 * <p>
 * 本主题主类仅供Windows下使用，它将使用Windows操作系统默认的字体等设置.
 * 
 * @author Jack Jiang(jb2011@163.com)
 * @version 1.0
 */
// * 参考源码基于JDK_1.6.0_u18.
public class BeautyEyeLookAndFeelWin extends WindowsLookAndFeel
{
	static{
		initLookAndFeelDecorated();
	}
	
	/**
	 * Instantiates a new beauty eye look and feel win.
	 *
	 * @see jb2011.lnf.beautyeye.BeautyEyeLNFHelper#implLNF()
	 * @see jb2011.lnf.beautyeye.ch20_filechooser.__UI__#uiImpl_win()
	 * @see #initForVista()
	 */
	public BeautyEyeLookAndFeelWin()
	{
		super();

		jb2011.lnf.beautyeye.BeautyEyeLNFHelper.implLNF();
		
		//自定义JFileChooser的L&F实现（为了解决windows LNF下文件选择框UI未实现背景填充问题）
		jb2011.lnf.beautyeye.ch20_filechooser.__UI__.uiImpl_win();
		
		//针对Vista及更新的windows平台进行特殊设置
		initForVista();
	}
	
	/**
	 * &#x56E0;Windos LNF&#x4F1A;&#x5728;Vista&#x53CA;&#x66F4;&#x65B0;&#x7684;&#x64CD;&#x4F5C;&#x7CFB;&#x7EDF;&#xFF08;&#x5982;win7&#xFF09;&#x4E0A;&#x5BF9;Windows LNF&#x4F5C;&#x9644;&#x52A0;&#x8BBE;&#x7F6E;&#xFF0C;
	 * &#x4EE5;&#x4FDD;&#x8BC1;&#x4E0E;Vista&#x53CA;&#x66F4;&#x65B0;&#x5E73;&#x53F0;&#x7684;&#x5916;&#x89C2;&#x7684;&#x4E00;&#x81F4;&#x6027;&#xFF08;&#x5982;&#x8BA9;&#x83DC;&#x5355;&#x83DC;&#x9879;&#x9AD8;&#x5EA6;&#x66F4;&#x5927;&#x7B49;&#xFF09;&#xFF0C;&#x8BF7;&#x53C2;&#x89C1;
	 * WindowsLookAndFeel.initVistaComponentDefaults(..)&#x3002;
	 * <p>
	 * BeautyEye&#x4E2D;&#x56E0;&#x9700;&#x8981;&#x4FDD;&#x8BC1;&#x5BA1;&#x7F8E;&#x4E00;&#x81F4;&#x6027;&#xFF08;&#x5728;&#x6240;&#x6709;win&#x5E73;&#x53F0;&#x4E0A;&#xFF09;&#x800C;&#x4E0D;&#x9700;&#x8981;&#x8FD9;&#x4E9B;&#x989D;&#x5916;&#x7684;&#x8BBE;&#x7F6E;&#xFF0C;
	 * &#x4F46;&#x56E0;&#x8BE5;&#x65B9;&#x6CD5;&#x662F;private&#x79C1;&#x6709;&#x65B9;&#x6CD5;&#xFF0C;&#x65E0;&#x6CD5;&#x8FDB;&#x884C;&#x8986;&#x76D6;&#x5C4F;&#x853D;&#xFF0C;&#x6240;&#x4EE5;&#x53EA;&#x80FD;&#x5728;&#x6B64;&#x5355;&#x5217;&#x65B9;&#x6CD5;&#xFF0C;&#x4EE5;&#x4FBF;&#x9488;&#x5BF9;Vista
	 * &#x53CA;&#x66F4;&#x65B0;&#x7684;&#x5E73;&#x53F0;&#x8FDB;&#x884C;&#x8865;&#x6551;&#x6027;&#x91CD;&#x65B0;&#x8BBE;&#x7F6E;&#x4EE5;&#x4FBF;&#x4E0E;BeautyEye LNF&#x7684;&#x5BA1;&#x6838;&#x8FDB;&#x884C;&#x9002;&#x914D;.
	 *
//	 * @see WindowsLookAndFeel.initVistaComponentDefaults(..)
	 */
	protected void initForVista()
	{
		if(WinUtils.isOnVista())
		{
			UIManager.put("CheckBoxMenuItem.margin",new InsetsUIResource(0,0,0,0));
			UIManager.put("RadioButtonMenuItem.margin",new InsetsUIResource(0,0,0,0));
			UIManager.put("Menu.margin",new InsetsUIResource(0,0,0,0));//windows lnf xp中默认是2，2，2，2
			UIManager.put("MenuItem.margin",new InsetsUIResource(0,0,0,0));//windows lnf中  xp默认是2，2，2，2
			
			UIManager.put("Menu.border",new BorderUIResource(BorderFactory.createEmptyBorder(1,3,2,3)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
			UIManager.put("MenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(1,0,2,0)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
			UIManager.put("CheckBoxMenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,2,4,2)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
			UIManager.put("RadioButtonMenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,0,4,0)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;		
//			UIManager.put("PopupMenu.border",new BorderUIResource(BorderFactory.createEmptyBorder(20,10,20,10)));//	
		
			UIManager.put("CheckBoxMenuItem.checkIcon"
					,new jb2011.lnf.beautyeye.ch9_menu.BECheckBoxMenuItemUI.CheckBoxMenuItemIcon().setUsedForVista(true));//javax.swing.plaf.basic.BasicIconFactory.CheckBoxMenuItemIcon);
			UIManager.put("RadioButtonMenuItem.checkIcon"
					,new jb2011.lnf.beautyeye.ch9_menu.BERadioButtonMenuItemUI.RadioButtonMenuItemIcon().setUsedForVista(true));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sun.java.swing.plaf.windows.WindowsLookAndFeel#getName()
	 */
	@Override 
	public String getName() 
	{
        return "BeautyEyeWin";
    }

	/* (non-Javadoc)
	 * @see com.sun.java.swing.plaf.windows.WindowsLookAndFeel#getID()
	 */
	@Override 
    public String getID() 
    {
        return "BeautyEyeWin";
    }

	/* (non-Javadoc)
	 * @see com.sun.java.swing.plaf.windows.WindowsLookAndFeel#getDescription()
	 */
	@Override 
    public String getDescription() 
    {
        return "BeautyEye windows-platform L&F developed by Jack Jiang(jb2011@163.com).";
    }
	
	/**
	 * Gets the supports window decorations.
	 *
	 * @return the supports window decorations
	 * {@inheritDoc}
	 */
	@Override 
	public boolean getSupportsWindowDecorations() 
	{
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected void initComponentDefaults(UIDefaults table)
    {
		super.initComponentDefaults(table);
        initOtherResourceBundle(table);
    }
	/**
     * Initialize the defaults table with the name of the other ResourceBundle
     * used for getting localized defaults.
     */
    protected void initOtherResourceBundle(UIDefaults table)
    {
        table.addResourceBundle( "jb2011.lnf.beautyeye.resources.beautyeye" );
    }
	
	/**
	 * 据BeautyEyeLNFHelper.frameBorderStyle指明的窗口边框类型来
	 * 决定是否使用操作系统相关的窗口装饰样式.
	 */
	static void initLookAndFeelDecorated()
	{
		if(BeautyEyeLNFHelper.frameBorderStyle == FrameBorderStyle.osLookAndFeelDecorated)
		{
			JFrame.setDefaultLookAndFeelDecorated(false);
			JDialog.setDefaultLookAndFeelDecorated(false);
		}
		else
		{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		}
		
//		UIManager.put("swing.aatext", Boolean.FALSE);
	}
}
