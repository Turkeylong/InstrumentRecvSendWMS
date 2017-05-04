package com.rfid.instrument.util;

import java.util.ArrayList;

import com.rfid.instrument.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class CustomDialog extends Dialog {  
	  
    public CustomDialog(Context context) {  
        super(context);  
    }  
  
    public CustomDialog(Context context, int theme) {  
        super(context, theme);  
    }
    
    public static class Builder
    {
    	private Context context;  
        private String title;
        private DialogInterface.OnClickListener okButtonClickListener;  
        private DialogInterface.OnClickListener cancleButtonClickListener;
    	private ArrayList<CheckBox> cb_flowmeterAccessories = new ArrayList<CheckBox>();
    	private ArrayList<CheckBox> cb_flowmeterAppearance = new ArrayList<CheckBox>();
    	private ArrayList<CheckBox> cb_sampleDocuments = new ArrayList<CheckBox>();
    	private Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7;
    	private EditText clientsRequirement,flowmeterRemarks,editText1,editText2;
        private int theme = Integer.MAX_VALUE;
        private Commission order = new Commission();
        public interface OnOkBtnClickListener
        {
        	public void onOkBtnClicked(Commission order);
        }
        private OnOkBtnClickListener listener;

		public Builder(Context context) {
			super();
			this.context = context;
		}
        public Builder(Context context, int theme) {
			super();
			this.context = context;
			this.theme = theme;
		}
        
		public Builder(Commission order, Context context, int theme) {
			// TODO 自动生成的构造函数存根
			this.order = order;
			this.context = context;
			this.theme = theme;
		}
		public Builder setTitle(int title) {  
            this.title = (String) context.getText(title);  
            return this;  
        } 
		
		public Builder setOkButton(OnOkBtnClickListener btnlistener,DialogInterface.OnClickListener listener)
		{
			this.listener = btnlistener;
			this.okButtonClickListener = listener;
			return this;
		}
		
		public Builder setCancleButton(DialogInterface.OnClickListener listener)
		{
			this.cancleButtonClickListener = listener;
			return this;
		}

		public CustomDialog create()
        {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            // instantiate the dialog with the custom Theme  
            final CustomDialog dialog = new CustomDialog(context,theme);  
            View layout = inflater.inflate(R.layout.dialog, null);  
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            
            spinner1=(Spinner) layout.findViewById(R.id.spinner1);
            spinner2=(Spinner) layout.findViewById(R.id.spinner2);
            spinner3=(Spinner) layout.findViewById(R.id.spinner3);
            spinner4=(Spinner) layout.findViewById(R.id.spinner4);
            spinner5=(Spinner) layout.findViewById(R.id.spinner5);
            spinner6=(Spinner) layout.findViewById(R.id.spinner6);
            spinner7=(Spinner) layout.findViewById(R.id.spinner7);
            
            clientsRequirement = (EditText) layout.findViewById(R.id.clientsRequirement);
            flowmeterRemarks = (EditText) layout.findViewById(R.id.flowmeterRemarks);
            
            editText1 = (EditText) layout.findViewById(R.id.editText1);
            editText2 = (EditText) layout.findViewById(R.id.editText2);
            
            //cb_flowmeterAccessories
            //cb_flowmeterAppearance
            //cb_sampleDocuments
            cb_flowmeterAccessories.add((CheckBox) layout.findViewById(R.id.checkBox1));
            cb_flowmeterAccessories.add((CheckBox) layout.findViewById(R.id.checkBox2));
            cb_flowmeterAccessories.add((CheckBox) layout.findViewById(R.id.checkBox3));
            cb_flowmeterAccessories.add((CheckBox) layout.findViewById(R.id.checkBox4));
            cb_flowmeterAccessories.add((CheckBox) layout.findViewById(R.id.checkBox5));
            
            cb_flowmeterAppearance.add((CheckBox) layout.findViewById(R.id.checkBox6));
            cb_flowmeterAppearance.add((CheckBox) layout.findViewById(R.id.checkBox7));
            cb_flowmeterAppearance.add((CheckBox) layout.findViewById(R.id.checkBox8));
            cb_flowmeterAppearance.add((CheckBox) layout.findViewById(R.id.checkBox9));
            cb_flowmeterAppearance.add((CheckBox) layout.findViewById(R.id.checkBox10));
            
            cb_sampleDocuments.add((CheckBox) layout.findViewById(R.id.checkBox11));
            cb_sampleDocuments.add((CheckBox) layout.findViewById(R.id.checkBox12));
            cb_sampleDocuments.add((CheckBox) layout.findViewById(R.id.checkBox13));
            cb_sampleDocuments.add((CheckBox) layout.findViewById(R.id.checkBox14));
            cb_sampleDocuments.add((CheckBox) layout.findViewById(R.id.checkBox15));
            
            Button okButton = (Button) layout.findViewById(R.id.okButton);
            Button cancleButton = (Button) layout.findViewById(R.id.cancleButton);
            okButton.setOnClickListener(new View.OnClickListener() {  
                public void onClick(View v) {  
                    okButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE); 

                    order.setAccessoriesFrontStraightPipe(spinner1.getSelectedItemPosition());
                    order.setAccessoriesRearStraightPipe(spinner2.getSelectedItemPosition());
                    order.setAccessoriesRectifier(spinner3.getSelectedItemPosition());
                    order.setAccessoriesJoint(spinner4.getSelectedItemPosition());
                    order.setAccessoriesShim(spinner5.getSelectedItemPosition());
                    order.setAccessoriesWasher(spinner6.getSelectedItemPosition());
                    order.setAccessoriesBoltNut(spinner7.getSelectedItemPosition());
                    
                    order.setClientsRequirement(clientsRequirement.getText().toString());
                    order.setRemarks(flowmeterRemarks.getText().toString());
                    
            		StringBuffer flowmeterAccessories = new StringBuffer();

            		 for(CheckBox tmp:cb_flowmeterAccessories)
            		 {
            			 if(tmp.isChecked())
            			 {
            				 flowmeterAccessories.append(tmp.getText()+"，");
            			 }
            		 }
            		 if(cb_flowmeterAccessories.get(4).isChecked())
            			 flowmeterAccessories.append(editText1.getText().toString());
            		 order.setFlowmeterAccessories(flowmeterAccessories.toString());
                    
            		StringBuffer flowmeterAppearance = new StringBuffer();

	           		 for(CheckBox tmp:cb_flowmeterAppearance)
	           		 {
            			 if(tmp.isChecked())
            			 {
            				 flowmeterAppearance.append(tmp.getText()+"，");
            			 } 
	           		 }
	           		 order.setFlowmeterAppearance(flowmeterAppearance.toString());
                    
            		StringBuffer sampleDocuments = new StringBuffer();
            		
	           		 for(CheckBox tmp:cb_sampleDocuments)
	           		 {
            			 if(tmp.isChecked())
            			 {
            				 sampleDocuments.append(tmp.getText()+"，");
            			 }
	           		 }
	           		if(cb_sampleDocuments.get(4).isChecked())
	           			sampleDocuments.append(editText2.getText().toString());
	           		 order.setSampleDocuments(sampleDocuments.toString());
                    
                    listener.onOkBtnClicked(order);
                }  
            });
            cancleButton.setOnClickListener(new View.OnClickListener() {  
                public void onClick(View v) {  
                	cancleButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);  
                }  
            });
            

            
            return dialog;
        }
    }
    

}
