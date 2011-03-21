/*
* $Id$
* Copyright (c) 2008-2010 Insprise Software (Shanghai) Co. Ltd.
* All Rights Reserved
* Changelog:
*   Li Guoliang - Oct 8, 2010: Initial version
*/

package
{
	import mx.collections.ArrayCollection;
	import mx.collections.ListCollectionView;
	import mx.controls.TextArea;
	import mx.controls.TextAreaAccessor;
	import mx.controls.dataGridClasses.DataGridColumn;

	import org.athenasource.framework.eo.core.ui.ErrorDisplay;

	/**
	 * TestDriveHelper
	 */
	public class Utils
	{

		// Array(Collection) Utils
		/**
		 * Creates a new array with the specified item as the first element and all the items in the given array.
		 */
		public static function insertToArray(array:Array, item:Object):Array {
			var newArray:Array = [item];
			for(var i:int = 0; i < array.length; i++) {
				newArray.push(array[i]);
			}
			return newArray;
		}

		/**
		 * Returns true if the given object is in the specified collection (using '==' for comparison) or false otherwise.
		 * @param object the object to look for
		 * @param collection the collection for looking up; if null the function always return false.
		 */
		public static function isIn(object:Object, collection:ArrayCollection):Boolean {
			for(var i:int = 0; collection != null && i < collection.length; i++) {
				if(object == collection.getItemAt(i)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Adds all the elements of fromAC to toAC.
		 * @param fromAC the source array collection; if it is null, nothing will be copied to toAC.
		 */
		public static function addAllElements(fromAC:ArrayCollection, toAC:ArrayCollection):void {
			for(var i:int = 0; fromAC != null && i < fromAC.length; i ++) {
				toAC.addItem(fromAC.getItemAt(i));
			}
		}

		/**
		 * Removes the first occurrence of the given element from the target ArrayCollection.
		 * @return the index where the occurrence is removed or -1 if not found.
		 */
		public static function removeFromArrayCollection(ac:ArrayCollection, element:Object):int {
			return removeFromListCollectionView(ac, element);
		}

		/**
		 * Removes the first occurrence of the given element from the target ListCollectionView.
		 * @return the index where the occurrence is removed or -1 if not found.
		 */
		public static function removeFromListCollectionView(list:ListCollectionView, element:Object):int {
			if(list == null) {
				return -1;
			}
			var index:int = list.getItemIndex(element);
			if(index >= 0) {
				list.removeItemAt(index);
			}
			return index;
		}

		// TextArea Utils
		/**
		 * Appends the specified message to the text area and scroll to the bottom or the text area.
		 * @appendNewLine true to append new line char '\n' after the message.
		 */
		public static function appendMessageAndScrollProperly(textArea:TextArea, mesg:String, appendNewLine:Boolean = true):void {
			if(mesg == null || mesg.length == 0) {
				return;
			}
			var line:int = TextAreaAccessor.getTextField(textArea).numLines;
			textArea.text = textArea.text + mesg + (appendNewLine ? "\n" : "");
			textArea.callLater(setTopLine, [textArea, line - 1]);
		}

		/** Sets the given line as the top line being displayed */
		private static function setTopLine(textArea:TextArea, lineIndex:int):void {
			textArea.verticalScrollPosition = lineIndex;
		}


	} // end class
} // end package