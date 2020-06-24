import java.util.Arrays;

/**
 * @Author liaojincheng
 * @Date 2020/4/30 10:44
 * @Version 1.0
 * @Description
 */
public class ddd {
    /**
     * 进行快速排序
     * @param arr
     * @param left 左指针
     * @param right 右指针
     * 思路:
     * get_mid{
     *       先选择一个基准数出来,
     *              从右往左向后找比 < 基准数pivot小的数
     *              从左往右向前找比 > 基准数pivot大的数
     *        最终返回排好序的pivot位置index
     *        }
     *
     *       这样一趟排序下来确定了一个数的位置
     *       而后以该数为中心,分成左右两半再进行排序
     *
     *       采用分而自治方法进行递归调用方法,退出条件为 left >= right
     *       quick_sort(arr, left, mid - 1)
     *       quick_sort(arr, mid + 1, right)
     * 注意事项:
     *              (必须从右往左先找,因为要覆盖基准数,不然会多余了一个基准数一样的值<从左往右找,该多余的基准数t,因为t==pivot,所以左指针直接++,直接跳过了>)
     *
     *              情况1: 右指针没有找到元素 < pivot,直接 left(0) == right,  pivot直接覆盖left(0),结束get_mid
     *              情况2: 右指针找到了1个元素a < pivot,   直接 left(0) == right(a), 而后左指针没有找到 > pivot ====>  left = right(a), left = pivot,结束get_mid
     *              情况3: 右指针找到了1个元素a < pivot,   直接 left(0) == right(a),
     *                     左指针也找到了1个元素b > pivot,    直接 right(a) = left(b),
     *                     而后右指针没有找到元素 < pivot了, 直接 left(b) == right  ====> left(b) = pivot
     *              如果总数是单数和情况2一样
     *              如果总数是双数和情况3一样
     */
    public static void quick_sort(int[] arr, int left, int right){
        if(left < right){
            int mid = get_mid(arr, left, right);
            quick_sort(arr, left, mid - 1);
            quick_sort(arr, mid + 1, right);
        }
    }



    public static int get_mid(int[] arr, int left, int right) {
        //取出基准数
        int pivot = arr[left];

        while(left < right){
            //先从右往左找 < pivot值的数
            while(arr[right] >= pivot && left < right){
                right--;
            }
            arr[left] = arr[right];

            while(arr[left] <= pivot && left < right){
                left++;
            }
            arr[right] = arr[left];
        }

        //排序好了left == right
        arr[left] = pivot;
        return left;
    }

    public static void main(String[] args) {
        //int[] arr = {7,6,5,4,9,4};
        /**
         * 通过{2,3,4}说明了
         *      外层while( left < right )
         *
         *      内层while(arr[right] >= pivot && left < right)
         *
         * 如果外层中有内层退出条件的,但是内层中没有写(因为外层中有), 当内层不满足该条件,并没有触发外层循环终止该内层循环,
         * 并且报了java.lang.ArrayIndexOutOfBoundsException: -1异常
         *
         * 所以外层中有了该退出条件,但是内层有该条件才能退出的话,内层也是要写上的.
         *
         */

        int[] arr = {2,3,4};
        System.out.println(Arrays.toString(arr));
        quick_sort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}

