package it.fabaris.wfp.widget;

/*
 * Copyright (C) 2009 University of Washington
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import org.javarosa.core.model.Constants;
import org.javarosa.form.api.FormEntryPrompt;

import android.content.Context;
import android.util.Log;

/**
 * Convenience class that handles creation of widgets.
 * 
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Fabaris Srl: Leonardo Luciani www.fabaris.it
 */
public class WidgetFactory {

	/**
	 * Returns the appropriate QuestionWidget for the given FormEntryPrompt.
	 * 
	 * @param fep
	 *            prompt element to be rendered
	 * @param context
	 *            Android context
	 */
	static public QuestionWidget createWidgetFromPrompt(FormEntryPrompt fep,
			Context context) {

		// get appearance hint and clean it up so it is lower case and never
		// null...
		String appearance = fep.getAppearanceHint();
		if (appearance == null)
			appearance = "";
		appearance = appearance.toLowerCase();

		QuestionWidget questionWidget = null;
		Log.i("WidgetFactory",
				"create a component with name "
						+ (fep.getLongText() == null ? (fep.getShortText() == null ? "N/A"
								: fep.getShortText())
								: fep.getLongText()));
		switch (fep.getControlType()) {
		case Constants.CONTROL_INPUT: // gruppo 1
			switch (fep.getDataType())
			{
			case Constants.DATATYPE_TEXT: // 1
				if (appearance.equals("numbers")) 
				{
					Log.i("WidgetFactory", "create StringNumberWidget, CONTROL_INPUT:Constants.DATATYPE_TEXT");
					questionWidget = new StringNumberWidget(context, fep);
				} 
				/*
				else if(appearance.equals("multi-label")) 
				{
					Log.i("WidgetFactory", "create StringNumberWidget, CONTROL_INPUT:Constants.DATATYPE_TEXT (MULTI LINE TEXT)");
					questionWidget = new StringMultiLineWidget(context, fep);
				}
				*/
				else
				{
					Log.i("WidgetFactory", "create StringWidget, CONTROL_INPUT:Constants.DATATYPE_TEXT");
					questionWidget = new StringWidget(context, fep);
				}
				break;

			case Constants.DATATYPE_INTEGER: // 2
				Log.i("WidgetFactory",
						"create IntegerWidget, CONTROL_INPUT:Constants.DATATYPE_INTEGER");
				questionWidget = new IntegerWidget(context, fep);
				// questionWidget = new DecimalWidget(context, fep);
				break;

			case Constants.DATATYPE_DECIMAL: // 3
				Log.i("WidgetFactory",
						"create DecimalWidget, CONTROL_INPUT:Constants.DATATYPE_DECIMAL");
				questionWidget = new DecimalWidget(context, fep);
				break;

			case Constants.DATATYPE_DATE: // 4
				Log.i("WidgetFactory",
						"create DateWidget, CONTROL_INPUT:Constants.DATATYPE_DATE");
				questionWidget = new DateWidget(context, fep);
				break;

			case Constants.DATATYPE_TIME: // 5
				Log.i("WidgetFactory",
						"create TimeWidget, CONTROL_INPUT:Constants.DATATYPE_TIME");
				questionWidget = new TimeWidget(context, fep);
				break;

			case Constants.DATATYPE_DATE_TIME: // 6
				Log.e("WidgetFactory",
						"create DateTimeWidget, CONTROL_INPUT:Constants.DATATYPE_DATE_TIME");
				questionWidget = new DateTimeWidget(context, fep);
				break;

			case Constants.DATATYPE_GEOPOINT: // 10
				Log.i("WidgetFactory",
						"create GeoPointWidget, CONTROL_INPUT:Constants.DATATYPE_GEOPOINT");
				questionWidget = new GeoPointWidget(context, fep);
				break;
			case Constants.DATATYPE_BARCODE: // 11
				Log.i("WidgetFactory",
						"create BarcodeWidget, CONTROL_INPUT:Constants.DATATYPE_BARCODE");
				questionWidget = new BarcodeWidget(context, fep);
				break;
				
			case Constants.DATATYPE_BINARY: 
				// crea un widget per acquisire una foto
				Log.i("WidgetFactory",
						"create BarcodeWidget, CONTROL_INPUT:Constants.DATATYPE_IMAGE");
				questionWidget = new ImageWidget(context, fep);
				break;

				
			default:
				Log.i("WidgetFactory",
						"create StringWidget, CONTROL_INPUT:default");
				questionWidget = new StringWidget(context, fep);

				break;
			}
			break;
		// case Constants.CONTROL_IMAGE_CHOOSE:
		// if (appearance.equals("web")) {
		// questionWidget = new ImageWebViewWidget(context, fep);
		// } else {
		// questionWidget = new ImageWidget(context, fep);
		// }
		// break;
		// case Constants.CONTROL_AUDIO_CAPTURE:
		// questionWidget = new AudioWidget(context, fep);
		// break;
		// case Constants.CONTROL_VIDEO_CAPTURE:
		// questionWidget = new VideoWidget(context, fep);
		// break;

		
			
		case Constants.CONTROL_SELECT_ONE: // gruppo 2
			if (appearance.contains("compact")) {
				int numColumns = -1;
				try {
					numColumns = Integer.parseInt(appearance
							.substring(appearance.indexOf('-') + 1));
				} catch (Exception e) {
					// Do nothing, leave numColumns as -1
					Log.e("WidgetFactory", "Exception parsing numColumns");
				}
				if (appearance.contains("quick")) {
					// qui non ci entrera' mai!!! Grande fabaris!
					questionWidget = new GridWidget(context, fep, numColumns,
							true);
				} else {
					Log.i("WidgetFactory",
							"create GridWidget, Constants.CONTROL_SELECT_ONE:compact");
					questionWidget = new GridWidget(context, fep, numColumns,
							false);
				}
			} else if (appearance.equals("minimal")) {
				Log.i("WidgetFactory",
						"create SpinnerWidget,Constants.CONTROL_SELECT_ONE: minimal (drop-down)");
				questionWidget = new SpinnerWidget(context, fep);
			}
			// else if (appearance != null &&
			// appearance.contains("autocomplete")) {
			// String filterType = null;
			// try {
			// filterType = appearance.substring(appearance.indexOf('-') + 1);
			// } catch (Exception e) {
			// // Do nothing, leave filerType null
			// Log.e("WidgetFactory", "Exception parsing filterType");
			// }
			// questionWidget = new AutoCompleteWidget(context, fep,
			// filterType);
			//
			// }
			
			
			else if (appearance.equals("quick")) {
				Log.i("WidgetFactory",
						"create SelectOneAutoAdvanceWidget, Constants.CONTROL_SELECT_ONE:quick");
				questionWidget = new SelectOneAutoAdvanceWidget(context, fep);
			} else if (appearance.equals("list")) {
				Log.i("WidgetFactory",
						"create ListWidget, Constants.CONTROL_SELECT_ONE:list");
				questionWidget = new ListWidget(context, fep, true);
			} else if (appearance.equals("list-nolabel")) {
				Log.i("WidgetFactory",
						"create ListWidget, Constants.CONTROL_SELECT_ONE:list-nolabel");
				questionWidget = new ListWidget(context, fep, false);
			} else if (appearance.equals("label")) {
				Log.i("WidgetFactory",
						"create LabelWidget, Constants.CONTROL_SELECT_ONE:label");
				questionWidget = new LabelWidget(context, fep);
			} else {
				Log.i("WidgetFactory",
						"create SelectOneWidget, Constants.CONTROL_SELECT_ONE: radioButton or roster");
				questionWidget = new SelectOneWidget(context, fep);
			}
			break;

		case Constants.CONTROL_SELECT_MULTI: // gruppo 3
			if (appearance.contains("compact")) {
				int numColumns = -1;
				try {
					numColumns = Integer.parseInt(appearance
							.substring(appearance.indexOf('-') + 1));
				} catch (Exception e) {
					// Do nothing, leave numColumns as -1
					Log.e("WidgetFactory", "Exception parsing numColumns");
				}
				Log.i("WidgetFactory",
						"create GridMultiWidget, Constants.CONTROL_SELECT_MULTI:compact");
				questionWidget = new GridMultiWidget(context, fep, numColumns);
			} else if (appearance.equals("minimal")) {
				Log.i("WidgetFactory",
						"create SpinnerMultiWidget, Constants.CONTROL_SELECT_MULTI:minimal");
				questionWidget = new SpinnerMultiWidget(context, fep);
			} else if (appearance.equals("list")) {
				Log.i("WidgetFactory",
						"create ListMultiWidget, Constants.CONTROL_SELECT_MULTI:list");
				questionWidget = new ListMultiWidget(context, fep, true);
			} else if (appearance.equals("list-nolabel")) {
				Log.i("WidgetFactory",
						"create ListMultiWidget, Constants.CONTROL_SELECT_MULTI:list-nolabel");
				questionWidget = new ListMultiWidget(context, fep, false);
			} else if (appearance.equals("label")) {
				Log.i("WidgetFactory",
						"create LabelWidget, Constants.CONTROL_SELECT_MULTI:label");
				questionWidget = new LabelWidget(context, fep);
			} else {
				Log.i("WidgetFactory",
						"create SelectMultiWidget, Constants.CONTROL_SELECT_MULTI:default");
				questionWidget = new SelectMultiWidget(context, fep);
			}
			break;
		case Constants.CONTROL_TRIGGER: // gruppo 9
			Log.i("WidgetFactory",
					"create TriggerWidget, Constants.CONTROL_TRIGGER");
			questionWidget = new TriggerWidget(context, fep);
			break;
			
		
		default: // gruppo default
			Log.i("WidgetFactory", "create StringWidget, default");
			questionWidget = new StringWidget(context, fep);
			break;
		}
		// questionWidget.setFormId(fep.getIndex().getNextLevel().getReference().getName(1).toString());
		questionWidget.setFormId(fep.getFormElement().getID());
		return questionWidget;
	}
}